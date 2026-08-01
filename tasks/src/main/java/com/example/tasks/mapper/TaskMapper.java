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
        if (task == null) {
            return null;
        }

        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .name(task.getName())
                .dueDate(task.getDueDate())
                .createdBy(task.getCreatedBy())
                .statusTypeId(task.getStatusType() != null ? task.getStatusType().getStatusTypeId() : null)
                .userId(task.getUser() != null ? task.getUser().getUserId() : null)
                .lastUpdatedBy(task.getLastUpdatedBy())
                .lastUpdateDate(task.getLastUpdateDate())
                .creationDate(task.getCreationDate())
                .createdByFullName(task.getCreatedByFullName())
                .build();
    }

    public Task toEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }

        return Task.builder()
                .name(taskDTO.getName())
                .dueDate(taskDTO.getDueDate())
                .statusType(taskDTO.getStatusTypeId() != null ? statusTypeRepository.getReferenceById(taskDTO.getStatusTypeId()) : null)
                .user(taskDTO.getUserId() != null ? userRepository.getReferenceById(taskDTO.getUserId()) : null)
                .createdByFullName(taskDTO.getCreatedByFullName())
                .build();
    }
}
