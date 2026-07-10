package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }
}
