package com.server.server.reminder_group;

import com.server.server.reminder.Reminder;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "reminder_group")
@Entity(name = "ReminderGroup")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReminderGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_date", nullable = false)
    private LocalDate groupDate;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders = new ArrayList<>();

    public ReminderGroup(LocalDate groupDate) {
        this.groupDate = groupDate;
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
        reminder.setGroup(this);

    }

    public void removeReminder(Reminder reminder) {
        reminders.remove(reminder);
        reminder.setGroup(null);
    }

}
