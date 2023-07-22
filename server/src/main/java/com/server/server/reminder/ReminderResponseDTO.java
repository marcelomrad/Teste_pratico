package com.server.server.reminder;

import java.time.LocalDate;

public record ReminderResponseDTO(Long id, String name, LocalDate date) {
    
    public ReminderResponseDTO(Reminder reminder) {
        this(reminder.getId(), reminder.getName(), reminder.getDate());
    }
}
