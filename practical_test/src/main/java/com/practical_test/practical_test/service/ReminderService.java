package com.practical_test.practical_test.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.practical_test.practical_test.reminder.Reminder;
import com.practical_test.practical_test.reminder.ReminderRepository;
import com.practical_test.practical_test.reminder.ReminderResponseDTO;
import com.practical_test.practical_test.reminder.ReminderRequestDTO;
import com.practical_test.practical_test.reminder_group.ReminderGroup;
import com.practical_test.practical_test.reminder_group.ReminderGroupRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.practical_test.practical_test.exception.BadRequestException;

@Service
public class ReminderService {

    @Autowired
    private  ReminderRepository reminderRepository;

    @Autowired
    private ReminderGroupRepository reminderGroupRepository;

    public void create(ReminderRequestDTO data) {

        this.validateDate(data);

        ReminderGroup group = findOrCreateGroupForDate(data.date());

        Reminder reminderData = new Reminder(data);

        reminderData.setGroup(group);

        if (group.getId() == null) {
            reminderGroupRepository.save(group);
        }

        reminderRepository.save(reminderData);

    }


    public List<ReminderResponseDTO> getAll() {
        List<ReminderGroup> groups = reminderGroupRepository.findAllByOrderByGroupDateAsc();

        if(groups.isEmpty()){
            throw new BadRequestException("Não há lembretes cadastrados");
        }

        return groups.stream()
                .flatMap(group -> group.getReminders().stream())
                .map(ReminderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllGrouped() {
        List<ReminderGroup> groups = reminderGroupRepository.findAll();

        if(groups.isEmpty()){
            throw new BadRequestException("Não há lembretes cadastrados");
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (ReminderGroup group : groups) {
            Map<String, Object> groupData = new HashMap<>();
            groupData.put("groupDate", group.getGroupDate());
            groupData.put("reminders", group.getReminders().stream().map(Reminder::getName).collect(Collectors.toList()));
            result.add(groupData);
        }

        return result;
    }

    public void deleteById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("Lembrete não encontrado"));

        ReminderGroup group = reminder.getGroup();
        if (group != null) {
            group.removeReminder(reminder);
            reminderGroupRepository.save(group);
        }

        reminderRepository.deleteById(id);
    }

    private void validateDate(ReminderRequestDTO data) {
        if (data.name() == null || data.name().isEmpty()) {
            throw new BadRequestException("O campo nome é obrigatório");
        }
    
        if (data.date() == null || data.date().toString().isEmpty()) {
            throw new BadRequestException("O campo data é obrigatório");
        }
        
        // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // try {
        //     dateFormatter.parse(data.date().toString());
        // } catch (DateTimeParseException e) {
        //     throw new IllegalArgumentException("O campo data não está no formato correto (yyyy-MM-dd)");
        // }
    
        LocalDate currentDate = LocalDate.now();
        LocalDate reminderDate = data.date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    
        // Verifica se a data do lembrete é anterior a data atual
        if (reminderDate.isBefore(currentDate)) {
            throw new BadRequestException("A data do lembrete não pode ser anterior à data atual");
        }
    }

    private ReminderGroup findOrCreateGroupForDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ReminderGroup group = reminderGroupRepository.findByGroupDate(localDate);
        if (group == null) {
            group = new ReminderGroup(localDate);
        }
        return group;
    }

}

