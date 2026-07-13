package com.example.tasks.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusTypeDTO {
    private String statusTypeId;
    private String statusName;
    private String createdBy;
    private LocalDate creationDate;
    private LocalDate lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}
