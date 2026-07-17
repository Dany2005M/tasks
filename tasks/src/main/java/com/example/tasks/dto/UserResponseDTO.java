package com.example.tasks.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    public Long userId;

    public String username;

    public String email;
}
