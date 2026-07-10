package com.example.tasks.mapper;

import com.example.tasks.domain.Task;
import com.example.tasks.dto.TaskDTO;
import com.example.tasks.repository.StatusTypeRepository;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final StatusTypeRepository statusTypeRepository;
    private final UserRepository userRepository;

    public TaskDTO toDTO(Task task) {
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .name(task.getName())
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy())
                .statusTypeId(task.getStatusType().getStatusTypeId())
                .userId(task.getUser().getUserId())
                .lastUpdatedBy(task.getLastUpdatedBy())
                .lastUpdateDate(task.getLastUpdateDate())
                .creationDate(task.getCreationDate())
                .createdByFullName(task.getCreatedByFullName())
                .build();
    }

    public Task toEntity(TaskDTO taskDTO) {
        return Task.builder()
                .name(taskDTO.getName())
                .dueDate(taskDTO.getDueDate())
                .statusType(statusTypeRepository.getReferenceById(taskDTO.getStatusTypeId()))
                .user(userRepository.getReferenceById(taskDTO.getUserId()))
                .createdByFullName(taskDTO.getCreatedByFullName())
                .build();
    }
}
