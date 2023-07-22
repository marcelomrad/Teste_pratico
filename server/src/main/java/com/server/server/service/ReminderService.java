package com.server.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.server.server.reminder.Reminder;
import com.server.server.reminder.ReminderRepository;
import com.server.server.reminder.ReminderResponseDTO;
import com.server.server.reminder.ReminderRequestDTO;
import com.server.server.reminder_group.ReminderGroup;
import com.server.server.reminder_group.ReminderGroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.server.server.exception.BadRequestException;

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
        List<ReminderGroup> groups = reminderGroupRepository.findAllByOrderByGroupDateAsc();
    
        if (groups.isEmpty()) {
            throw new BadRequestException("Não há lembretes cadastrados");
        }
    
        List<Map<String, Object>> result = new ArrayList<>();
    
        for (ReminderGroup group : groups) {
            Map<String, Object> groupData = new HashMap<>();
            groupData.put("groupDate", group.getGroupDate());
            
            List<Map<String, Object>> reminders = group.getReminders().stream()
                .map(reminder -> {
                    Map<String, Object> reminderData = new HashMap<>();
                    reminderData.put("id", reminder.getId());
                    reminderData.put("name", reminder.getName());
                    return reminderData;
                })
                .collect(Collectors.toList());
            
            groupData.put("reminders", reminders);
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
    
            // Verificar se o grupo não tem mais lembretes e excluir o grupo se estiver vazio
            if (group.getReminders().isEmpty()) {
                reminderGroupRepository.deleteById(group.getId());
            }
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
        
      
        LocalDate currentDate = LocalDate.now();
        LocalDate reminderDate = data.date();
    
        // Verifica se a data do lembrete é anterior a data atual
        if (reminderDate.isBefore(currentDate)) {
            throw new BadRequestException("A data do lembrete não pode ser anterior à data atual");
        }
    }

    private ReminderGroup findOrCreateGroupForDate(LocalDate date) {
        LocalDate localDate = date;
        ReminderGroup group = reminderGroupRepository.findByGroupDate(localDate);
        if (group == null) {
            group = new ReminderGroup(localDate);
        }
        return group;
    }

}

