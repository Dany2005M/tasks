package com.example.tasks.controller;

import com.example.tasks.dto.TaskDTO;
import com.example.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
@CrossOrigin()
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

    @GetMapping("/sorted-by-due-date")
    public List<TaskDTO> getTasksSortedByDueDate() {
        return taskService.getTasksSortedByDueDate();
    }

    @GetMapping("/status/{status}")
    public List<TaskDTO> getTasksByStatusName(@PathVariable String status) {
        return taskService.getTasksByStatusName(status);
    }

    @GetMapping("/earlier/{date}")
    public List<TaskDTO> getTasksEarlierThan(@PathVariable LocalDate date) {
        return taskService.getTasksEarlierThan(date);
    }

    @GetMapping("/stats")
    public Map<String, Long> getTaskCountGroupedByStatus() {
        return taskService.getTaskCountGroupedByStatus();
    }

    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestParam(required = false) String subject,
                                     @RequestParam(required = false) String assignedTo,
                                     @RequestParam(required = false) LocalDate dueDate,
                                     @RequestParam(required = false) String status) {

        return taskService.searchTasks(subject,assignedTo,dueDate,status);
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

    @PatchMapping("/{id}/status/{statusId}")
    public TaskDTO updateTaskStatus(@PathVariable Long id, @PathVariable String statusId) {
        return taskService.updateTaskStatus(id, statusId);
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
