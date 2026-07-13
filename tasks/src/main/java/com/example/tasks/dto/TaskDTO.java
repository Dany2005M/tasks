package com.example.tasks.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private Long taskId;
    private String name;
    private String statusTypeId;
    private Long userId;
    private LocalDate dueDate;
    private String createdBy;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}
