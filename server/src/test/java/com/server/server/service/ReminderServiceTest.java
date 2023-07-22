package com.server.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.server.server.exception.BadRequestException;
import com.server.server.reminder.Reminder;
import com.server.server.reminder.ReminderRepository;
import com.server.server.reminder.ReminderRequestDTO;
import com.server.server.reminder_group.ReminderGroup;
import com.server.server.reminder_group.ReminderGroupRepository;

@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {
    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private ReminderGroupRepository reminderGroupRepository;

    @InjectMocks
    private ReminderService reminderService;

    @Test
    public void testGetAll() {
        ReminderGroup group1 = new ReminderGroup(LocalDate.of(2024, 1, 1));
        Reminder reminder1 = new Reminder(1l, "Reminder 1", LocalDate.of(2024, 1, 1), group1);
        Reminder reminder2 = new Reminder(2l, "Reminder 2", LocalDate.of(2024, 1, 1), group1);
        
        group1.addReminder(reminder1);
        group1.addReminder(reminder2);

        ReminderGroup group2 = new ReminderGroup(LocalDate.of(2024, 1, 2));
        Reminder reminder3 = new Reminder(3l, "Reminder 3", LocalDate.of(2024, 1, 2), group2);
        group2.addReminder(reminder3);

        when(reminderGroupRepository.findAllByOrderByGroupDateAsc()).thenReturn(Arrays.asList(group1, group2));

        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> group1Map = new HashMap<>();
       
        group1Map.put("groupDate", LocalDate.of(2024, 1, 1));
        List<Map<String, Object>> group1Reminders = Arrays.asList(reminder1, reminder2).stream().map(reminder -> {
            Map<String, Object> reminderMap = new HashMap<>();
            reminderMap.put("id", reminder.getId());
            reminderMap.put("name", reminder.getName());
            return reminderMap;
        }).collect(Collectors.toList());
        group1Map.put("reminders", group1Reminders);
        expected.add(group1Map);

        Map<String, Object> group2Map = new HashMap<>();
        group2Map.put("groupDate", LocalDate.of(2024, 1, 2));
        List<Map<String, Object>> group2Reminders = Arrays.asList(reminder3).stream().map(reminder -> {
            Map<String, Object> reminderMap = new HashMap<>();
            reminderMap.put("id", reminder.getId());
            reminderMap.put("name", reminder.getName());
            return reminderMap;
        }).collect(Collectors.toList());
        group2Map.put("reminders", group2Reminders);
        expected.add(group2Map);

        List<Map<String, Object>> actual = reminderService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteById() {
        Reminder reminder = new Reminder(1L, "Reminder 1", LocalDate.of(2024, 1, 1), null);
       
        ReminderGroup group = new ReminderGroup(LocalDate.of(2024, 1, 1));
      
        reminder.setGroup(group);

        when(reminderRepository.findById(1L)).thenReturn(java.util.Optional.of(reminder));

        reminderService.deleteById(1L);

        verify(reminderRepository).deleteById(1L);
        assertEquals(null, reminder.getGroup());
    }

    @Test
    public void testCreateReminder() {
    ReminderGroup group = new ReminderGroup(LocalDate.of(2023, 10, 31));

    ReminderRequestDTO reminderRequestDTO = new ReminderRequestDTO("Reminder 1", LocalDate.of(2023, 10, 31));

    reminderService.create(reminderRequestDTO);

    ArgumentCaptor<Reminder> argumentCaptor = ArgumentCaptor.forClass(Reminder.class);
    verify(reminderRepository).save(argumentCaptor.capture());
    assertEquals("Reminder 1", argumentCaptor.getValue().getName());
    assertEquals(group, argumentCaptor.getValue().getGroup());
    }


    @Test
    public void testDeleteByIdThrowsBadRequestException() {
        when(reminderRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        assertThrows(BadRequestException.class, () -> {
            reminderService.deleteById(1L);
        });
        
        assertEquals("Lembrete não encontrado", assertThrows(BadRequestException.class, () -> {
            reminderService.deleteById(1L);
        }).getMessage());

    }
}