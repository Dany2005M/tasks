package com.example.tasks.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long userId;
    private String username;
    private LocalDate birthDate;
    private Boolean isInternal;
    private LocalDate creationDate;
    private String createdBy;
    private LocalDate lastUpdateDate;
    private String lastUpdatedBy;
    private String createdByFullName;
}
