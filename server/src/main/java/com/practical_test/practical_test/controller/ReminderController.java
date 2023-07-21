package com.server.server.controller;

import com.server.server.reminder.ReminderResponseDTO;
import com.server.server.reminder.ReminderRequestDTO;
import com.server.server.service.ReminderService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private  ReminderService reminderService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ReminderRequestDTO data) {
        reminderService.create(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Lembrete criado com sucesso!");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReminderResponseDTO>> getAll() {
        List<ReminderResponseDTO> reminders = reminderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(reminders);
    }

    @GetMapping("/grouped")
    public ResponseEntity<List<Map<String, Object>>> getAllGrouped() {
        List<Map<String, Object>> groupedReminders = reminderService.getAllGrouped();
        return ResponseEntity.status(HttpStatus.OK).body(groupedReminders);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        reminderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Lembrete deletado com sucesso!");
    }
}
