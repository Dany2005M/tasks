package com.example.tasks.mapper;

import com.example.tasks.domain.StatusType;
import com.example.tasks.dto.StatusTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class StatusTypeMapper {
    public StatusTypeDTO toDto(StatusType statusType) {
        return StatusTypeDTO.builder()
                .statusTypeId(statusType.getStatusTypeId())
                .statusName(statusType.getStatusName())
                .createdBy(statusType.getCreatedBy())
                .creationDate(statusType.getCreationDate())
                .lastUpdateDate(statusType.getLastUpdateDate())
                .lastUpdatedBy(statusType.getLastUpdatedBy())
                .createdByFullName(statusType.getCreatedByFullName())
                .build();
    }

    public StatusType toEntity(StatusTypeDTO statusTypeDTO) {
        return StatusType.builder()
                .statusName(statusTypeDTO.getStatusName())
                .createdByFullName(statusTypeDTO.getCreatedByFullName())
                .build();
    }

}
