package com.server.server.reminder_group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ReminderGroupRepository extends JpaRepository<ReminderGroup, Long> {

    ReminderGroup findByGroupDate(LocalDate groupDate);
    
    List<ReminderGroup> findAllByOrderByGroupDateAsc();
    
}
