package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.dto.StatusTypeDTO;
import com.example.tasks.mapper.StatusTypeMapper;
import com.example.tasks.repository.StatusTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusTypeService {
    private final StatusTypeRepository statusTypeRepository;
    private final StatusTypeMapper statusMapper;

    public List<StatusTypeDTO> getAllStatuses() {
        log.info("Statuses retrieved!");
        return statusTypeRepository.findAll()
                .stream()
                .map(statusMapper::toDto)
                .toList();
    }

    public StatusTypeDTO getStatusById(String id) {
        log.info("Status with id {} retrieved!", id);

        return statusTypeRepository.findById(id)
                .map(statusMapper::toDto)
                .orElse(null);
    }

    public List<StatusTypeDTO> getStatusesByName(String statusName) {
        log.info("Statuses retrieved with name {} retrieved!",  statusName);

        return statusTypeRepository.findByStatusName(statusName)
                .stream()
                .map(statusMapper::toDto)
                .toList();
    }

    @Transactional
    public StatusTypeDTO createStatus(StatusTypeDTO statusTypeDTO) {
        log.info("Status created!");

        StatusType status  = statusMapper.toEntity(statusTypeDTO);
        StatusType savedStatus = statusTypeRepository.save(status);

        return statusMapper.toDto(savedStatus);
    }

    @Transactional
    public List<StatusTypeDTO> createStatuses(List<StatusTypeDTO> statusTypeDTOs) {
        log.info("Statuses created!");

        List<StatusType> statuses = statusTypeDTOs.stream()
                .map(statusMapper::toEntity)
                .toList();

        statusTypeRepository.saveAll(statuses);

        return getAllStatuses();
    }

    @Transactional
    public StatusTypeDTO updateStatus(String id, StatusTypeDTO statusTypeDTO) {
        log.info("Status with id {} updated!", id);

        StatusType existingStatus = statusTypeRepository.findById(id)
                .orElse(null);

        StatusType savedStatus = null;

        if (existingStatus != null) {
            existingStatus.setStatusName(statusTypeDTO.getStatusName());
            existingStatus.setCreatedByFullName(statusTypeDTO.getCreatedByFullName());

            savedStatus = statusTypeRepository.save(existingStatus);
        }

        return statusMapper.toDto(savedStatus);
    }

    @Transactional
    public void deleteAllStatuses() {
        log.info("Statuses deleted!");

        statusTypeRepository.deleteAll();
    }

    @Transactional
    public List<StatusTypeDTO> deleteStatusById(String id) {
        log.info("Status with id {} deleted!", id);

        statusTypeRepository.deleteById(id);

        return getAllStatuses();
    }


}
