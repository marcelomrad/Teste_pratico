package com.practical_test.practical_test.controller;

import com.lembrete.lembrete.reminder.Reminder;
import com.lembrete.lembrete.reminder.ReminderRepository;
import com.lembrete.lembrete.reminder.ReminderResponseDTO;
import com.lembrete.lembrete.reminder.ReminderRequestDTO;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;




import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class LembreteController {

    @Autowired
    private ReminderRepository reminderRepository;

    @PostMapping("/create")
    public void saveReminder (@RequestBody ReminderRequestDTO data){
        Reminder reminderData = new Reminder(data);
        reminderRepository.save(reminderData);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ReminderResponseDTO>> getAll (){

        List<ReminderResponseDTO> reminders = reminderRepository.findAll().stream().map(ReminderResponseDTO::new).toList();
        
        return ResponseEntity.status(HttpStatus.OK).body(reminders);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReminder (@PathVariable Long id){
        reminderRepository.deleteById(id);
    }
}

