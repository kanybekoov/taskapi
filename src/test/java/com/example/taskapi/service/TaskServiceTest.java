package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import com.example.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTask() {
        Task task = new Task("Test Title", "Test Description", false);
        when(taskRepository.save(task)).thenReturn(task);

        Task result = taskService.create(task);

        assertThat(result).isEqualTo(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(
                new Task("Task 1", "Desc 1", false),
                new Task("Task 2", "Desc 2", true)
        );
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Task 1");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetByIdFound() {
        Task task = new Task("Find", "Desc", true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Find");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(taskRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Task> result = taskService.getById(999L);

        assertThat(result).isEmpty();
        verify(taskRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(5L);

        taskService.delete(5L);

        verify(taskRepository, times(1)).deleteById(5L);
    }

    @Test
    void testUpdateTask() {
        Task oldTask = new Task("Old", "Old Desc", false);
        Task updatedTask = new Task("New", "New Desc", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.update(1L, updatedTask);

        assertThat(result.getTitle()).isEqualTo("New");
        assertThat(result.getDescription()).isEqualTo("New Desc");
        assertThat(result.isCompleted()).isTrue();

        verify(taskRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }
}
