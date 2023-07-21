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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    @Autowired
    private  ReminderRepository reminderRepository;

    @Autowired
    private ReminderGroupRepository reminderGroupRepository;

    public void create(ReminderRequestDTO data) {

        validateDate(data);

        Reminder reminderData = new Reminder(data);
        reminderRepository.save(reminderData);

        ReminderGroup group = findOrCreateGroupForDate(data.date());
        group.addReminder(reminderData);

    }


    public List<ReminderResponseDTO> getAll() {
        List<ReminderGroup> groups = reminderGroupRepository.findAllByOrderByGroupDateAsc();

        if(groups.isEmpty()){
            throw new IllegalArgumentException("Não há lembretes cadastrados");
        }

        return groups.stream()
                .flatMap(group -> group.getReminders().stream())
                .map(ReminderResponseDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Lembrete não encontrado"));

        ReminderGroup group = reminder.getGroup();
        if (group != null) {
            group.removeReminder(reminder);
            reminderGroupRepository.save(group);
        }

        reminderRepository.deleteById(id);
    }

    private void validateDate(ReminderRequestDTO data) {
       if(data.name() == null || data.name().isEmpty()){
            throw new IllegalArgumentException("O campo nome é obrigatório");
        }

        if(data.date() == null){
            throw new IllegalArgumentException("O campo data é obrigatório");
        }

        LocalDate currentDate = LocalDate.now();
        LocalDate reminderDate = LocalDate.parse(data.date().toString());

        //Verifica se a data do lembrete é anterior a data atual
        if(reminderDate.isBefore(currentDate)){
            throw new IllegalArgumentException("A data do lembrete não pode ser anterior a data atual");
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

