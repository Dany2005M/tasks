package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
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

    @PostMapping
    public TaskDTO addTask(@Valid @RequestBody TaskDTO task) {
        return taskService.addTask(task);
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
