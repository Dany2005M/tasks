package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/user/{id}")
    public List<TaskDTO> getTasksByUserId(@PathVariable Long id) {
        return taskService.getTasksByUserId(id);
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PostMapping("/multiple-tasks")
    public List<TaskDTO> createTasks(@RequestBody @Valid List<TaskDTO> taskDTOs) {
        return taskService.createTasks(taskDTOs);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody @Valid TaskDTO taskDTO) {
        return taskService.updateTask(id, taskDTO);
    }

    @DeleteMapping
    public void deleteAllTasks() {
        taskService.deleteAllTasks();
    }

    @DeleteMapping("/{id}")
    public List<TaskDTO> deleteTaskById(@PathVariable Long id) {
        return taskService.deleteTaskById(id);
    }
}
