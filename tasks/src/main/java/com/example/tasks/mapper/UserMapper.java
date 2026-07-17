package com.example.tasks.mapper;

import com.example.tasks.domain.User;
import com.example.tasks.dto.UserDTO;
import com.example.tasks.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if(user == null) {
            return null;
        }

        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .isInternal(user.getIsInternal())
                .email(user.getEmail())
                .creationDate(user.getCreationDate())
                .createdBy(user.getCreatedBy())
                .lastUpdateDate(user.getLastUpdateDate())
                .lastUpdatedBy(user.getLastUpdatedBy())
                .createdByFullName(user.getCreatedByFullName())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        if(userDTO == null) {
            return null;
        }

        return User.builder()
                .username(userDTO.getUsername())
                .birthDate(userDTO.getBirthDate())
                .isInternal(userDTO.getIsInternal())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .createdByFullName(userDTO.getCreatedByFullName())
                .build();
    }

    public UserResponseDTO toResponseDTO(User user) {
        if(user == null) {
            return null;
        }

        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
