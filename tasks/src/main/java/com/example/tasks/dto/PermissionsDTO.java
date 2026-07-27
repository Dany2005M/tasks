package com.example.tasks.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionsDTO {
    private Long permissionId;
    private String permissionAction;
    private String permissionResource;
}
