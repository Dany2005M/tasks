package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.dto.TaskDTO;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.repository.StatusTypeRepository;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
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
    private final StatusTypeRepository statusTypeRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskDTO> getAllTasks() {
        log.info("Tasks retrieved!");

        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Task with id {} retrieved!", id);

        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElse(null);
    }

    public List<TaskDTO> getTasksByUserId(Long userId) {
        log.info("Tasks with user id {} retrieved!", userId);

        return taskRepository.findByUser_UserId(userId)
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

    @Transactional
    public List<TaskDTO> createTasks(List<TaskDTO> taskDTOs) {
        log.info("Tasks created!");

        List<Task> tasks = taskDTOs.stream()
                .map(taskMapper::toEntity)
                .toList();

        taskRepository.saveAll(tasks);
        return getAllTasks();
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Task with id {} updated!", id);

        Task existingTask = taskRepository.findById(id)
                .orElse(null);

        Task savedTask = null;

        if(existingTask != null) {
            existingTask.setName(taskDTO.getName());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setStatusType(statusTypeRepository.getReferenceById(taskDTO.getStatusTypeId()));
            existingTask.setUser(userRepository.getReferenceById(taskDTO.getUserId()));
            existingTask.setCreatedByFullName(taskDTO.getCreatedByFullName());

            savedTask = taskRepository.save(existingTask);
        }

        return taskMapper.toDTO(savedTask);
    }

    @Transactional
    public void deleteAllTasks() {
        log.info("Tasks deleted!");

        taskRepository.deleteAll();
    }

    @Transactional
    public List<TaskDTO> deleteTaskById(Long id) {
        log.info("Task with id {} deleted!", id);

        taskRepository.deleteById(id);

        return getAllTasks();
    }


}
