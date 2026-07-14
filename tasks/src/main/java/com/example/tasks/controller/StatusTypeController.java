package com.example.tasks.controller;

import com.example.tasks.dto.StatusTypeDTO;
import com.example.tasks.service.StatusTypeService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
@Validated
@CrossOrigin
public class StatusTypeController {
    private final StatusTypeService statusTypeService;

    public StatusTypeController(StatusTypeService statusTypeService) {
        this.statusTypeService = statusTypeService;
    }

    @GetMapping
    public List<StatusTypeDTO> getAllStatuses() {
        return statusTypeService.getAllStatuses();
    }

    @GetMapping("/{id}")
    public StatusTypeDTO getStatusById(@PathVariable String id) {
        return statusTypeService.getStatusById(id);
    }

    @GetMapping("/name/{statusName}")
    public List<StatusTypeDTO> getStatusesByName(@PathVariable String statusName) {
        return statusTypeService.getStatusesByName(statusName);
    }

    @PostMapping
    public StatusTypeDTO createStatus(@RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.createStatus(statusTypeDTO);
    }

    @PostMapping("/multiple-statuses")
    public List<StatusTypeDTO> createMultipleStatuses(@RequestBody @Valid List<StatusTypeDTO> statusTypeDTOs) {
        return statusTypeService.createStatuses(statusTypeDTOs);
    }

    @PutMapping("/{id}")
    public StatusTypeDTO updateStatus(@PathVariable String id, @RequestBody @Valid StatusTypeDTO statusTypeDTO) {
        return statusTypeService.updateStatus(id, statusTypeDTO);
    }

    @DeleteMapping
    public void deleteAllStatuses() {
        statusTypeService.deleteAllStatuses();
    }

    @DeleteMapping("/{id}")
    public List<StatusTypeDTO> deleteStatus(@PathVariable String id) {
        return statusTypeService.deleteStatusById(id);
    }


}
