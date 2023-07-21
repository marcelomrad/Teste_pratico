package com.practical_test.practical_test.reminder;

import jakarta.persistence.*;
import java.util.Date;

import com.practical_test.practical_test.reminder_group.ReminderGroup;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "reminder")
@Entity(name = "Reminder")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Reminder {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private ReminderGroup group;

    public Reminder(ReminderRequestDTO data) {
        this.name = data.name();
        this.date = data.date();
    }

    public void setGroup(ReminderGroup group) {
        this.group = group;
    }

}
