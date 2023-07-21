package com.server.server.reminder;

import java.util.Date;

public record ReminderResponseDTO(Long id, String name, Date date) {
    
    public ReminderResponseDTO(Reminder reminder) {
        this(reminder.getId(), reminder.getName(), reminder.getDate());
    }
}
