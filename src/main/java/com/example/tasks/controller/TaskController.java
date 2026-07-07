package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/sorted-by-due-date")
    public List<TaskDTO> getTasksSortedByDueDate() {
        return taskService.getTasksSortedByDueDate();
    }

    @GetMapping("/status/{status}")
    public List<TaskDTO> getTasksByStatus(@PathVariable String status) {
        return taskService.getTasksByStatus(status);
    }

    @GetMapping("/search/{keyword}")
    public List<TaskDTO> searchTasksByKeyword(@PathVariable String keyword) {
        return taskService.searchTasksByKeyword(keyword);
    }

    @GetMapping("/earlier/{date}")
    public List<TaskDTO> getTasksEarlierThan(@PathVariable LocalDateTime date) {
        return taskService.getTasksEarlierThan(date);
    }

    @GetMapping("/stats")
    public Map<String, Long> getTaskCountByStatus() {
        return taskService.getTaskCountByStatus();
    }

    @PostMapping
    public TaskDTO addTask(@Valid @RequestBody TaskDTO task) {
        return taskService.addTask(task);
    }

    @PostMapping("/multiple-tasks")
    public List<TaskDTO> addTasks(@Valid @RequestBody List<TaskDTO> tasks) {
        return taskService.addTasks(tasks);
    }

    @DeleteMapping
    public void deleteAllTasks() {
        taskService.deleteAllTasks();
    }

    @DeleteMapping("/{id}")
    public List<TaskDTO> deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@Valid @RequestBody TaskDTO task, @PathVariable Long id) {
        return taskService.updateTaskById(id,task);
    }

    @PatchMapping("/{id}/status/{status}")
    public TaskDTO updateTaskStatus(@PathVariable Long id, @PathVariable String status) {
        return taskService.updateTaskStatus(id,status);
    }

}
