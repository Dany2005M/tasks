package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.dto.TaskDTO;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        log.info("Tasks retrieved!");

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Task created!");

        Task task = taskMapper.toEntity(taskDTO);
        Task savedTask = taskRepository.save(task);

        return taskMapper.toDTO(savedTask);
    }
}
