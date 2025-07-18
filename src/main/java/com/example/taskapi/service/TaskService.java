package com.example.taskapi.service;

import com.example.taskapi.model.Task;
import com.example.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

@Service
public class TaskService {
    private final TaskRepository repository;
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final EmailService emailService;


    public TaskService(TaskRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task create(Task task) {
        Task created = repository.save(task);
        emailService.sendTaskCreated(created, "someonebek1@gmail.com");
        log.info("Task created: {}", created);
        return created;
    }

    @Cacheable("tasks")
    public List<Task> getAll() {
        log.info("Data UPDATED: So now Get list of Tasks from DATABASE (not from CACHE)");
        return repository.findAll();
    }

    public Optional<Task> getById(Long id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            log.info("Get task by id {}: {}", id, task.get());
        } else {
            log.warn("Task with id {} not found!", id);
        }
        return task;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(Long id) {
        repository.deleteById(id);
        log.info("Task deleted with id {}", id);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task update(Long id, Task updated) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(updated.getTitle());
                    task.setDescription(updated.getDescription());
                    task.setCompleted(updated.isCompleted());
                    Task saved = repository.save(task);
                    log.info("Task updated (id={}): {}", id, saved);
                    return saved;
                })
                .orElseThrow();
    }
}
