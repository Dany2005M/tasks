package com.example.tasks.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class LegacyTaskDTO {
    @NotNull(message = "The ID can't be null")
    private Long id;

    @NotBlank(message = "The content can't be null or empty")
    private String content;

    @NotNull(message = "The due date can't be null")
    private LocalDateTime dueDate;

    @NotBlank(message = "The status can't be null or empty")
    private String status;
}
