package com.server.server.reminder;

import java.time.LocalDate;

public record ReminderRequestDTO(String name, LocalDate date) {
    

}
