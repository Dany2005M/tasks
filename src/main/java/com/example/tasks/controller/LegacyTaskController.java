package com.example.tasks.controller;

import com.example.tasks.dto.LegacyTaskDTO;
import com.example.tasks.service.LegacyTaskService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@Validated
public class LegacyTaskController {
    private final LegacyTaskService legacyTaskService;

    public LegacyTaskController(LegacyTaskService legacyTaskService) {
        this.legacyTaskService = legacyTaskService;
    }

    @GetMapping
    public List<LegacyTaskDTO> getTasks() {
        return legacyTaskService.getTasks();
    }

    @GetMapping("/{id}")
    public LegacyTaskDTO getTaskById(@PathVariable Long id) {
        return legacyTaskService.getTaskById(id);
    }

    @GetMapping("/sorted-by-due-date")
    public List<LegacyTaskDTO> getTasksSortedByDueDate() {
        return legacyTaskService.getTasksSortedByDueDate();
    }

    @GetMapping("/status/{status}")
    public List<LegacyTaskDTO> getTasksByStatus(@PathVariable String status) {
        return legacyTaskService.getTasksByStatus(status);
    }

    @GetMapping("/search/{keyword}")
    public List<LegacyTaskDTO> searchTasksByKeyword(@PathVariable String keyword) {
        return legacyTaskService.searchTasksByKeyword(keyword);
    }

    @GetMapping("/earlier/{date}")
    public List<LegacyTaskDTO> getTasksEarlierThan(@PathVariable LocalDateTime date) {
        return legacyTaskService.getTasksEarlierThan(date);
    }

    @GetMapping("/stats")
    public Map<String, Long> getTaskCountByStatus() {
        return legacyTaskService.getTaskCountByStatus();
    }

    @PostMapping
    public LegacyTaskDTO addTask(@Valid @RequestBody LegacyTaskDTO task) {
        return legacyTaskService.addTask(task);
    }

    @PostMapping("/multiple-tasks")
    public List<LegacyTaskDTO> addTasks(@Valid @RequestBody List<LegacyTaskDTO> tasks) {
        return legacyTaskService.addTasks(tasks);
    }

    @DeleteMapping
    public void deleteAllTasks() {
        legacyTaskService.deleteAllTasks();
    }

    @DeleteMapping("/{id}")
    public List<LegacyTaskDTO> deleteTaskById(@PathVariable Long id) {
        return legacyTaskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public LegacyTaskDTO updateTask(@Valid @RequestBody LegacyTaskDTO task, @PathVariable Long id) {
        return legacyTaskService.updateTaskById(id,task);
    }

    @PatchMapping("/{id}/status/{status}")
    public LegacyTaskDTO updateTaskStatus(@PathVariable Long id, @PathVariable String status) {
        return legacyTaskService.updateTaskStatus(id,status);
    }

}
