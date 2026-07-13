package com.example.tasks.service;


import com.example.tasks.dto.LegacyTaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LegacyTaskService {
    private List<LegacyTaskDTO> tasks = new ArrayList<>();

    public List<LegacyTaskDTO> getTasks() {
        log.info("Getting tasks: ");

        return tasks;
    }

    public LegacyTaskDTO getTaskById(Long id) {
        log.info("Getting task by id: {}", id);
        return tasks.stream()
                .filter(legacyTaskDTO -> legacyTaskDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<LegacyTaskDTO> getTasksSortedByDueDate() {
        log.info("Getting tasks sorted by due date...");

        return tasks.stream()
                .sorted(Comparator.comparing(LegacyTaskDTO::getDueDate))
                .toList();
    }

    public List<LegacyTaskDTO> getTasksByStatus(String status) {
        log.info("Getting tasks by status: {}", status);

        return tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .toList();
    }

    public List<LegacyTaskDTO> getTasksEarlierThan(LocalDateTime date) {
        log.info("Getting tasks due date earlier than date: {}", date);

        return tasks.stream()
                .filter(task -> task.getDueDate().isBefore(date))
                .toList();
    }

    public Map<String, Long> getTaskCountByStatus() {
        log.info("Getting task count by status...");

        return tasks.stream()
                .collect(Collectors.groupingBy(LegacyTaskDTO::getStatus, Collectors.counting()));
    }


    public List<LegacyTaskDTO> searchTasksByKeyword(String keyword) {
        log.info("Searching tasks by keyword: {}", keyword);

        return tasks.stream()
                .filter(task -> task.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }


    public LegacyTaskDTO addTask(LegacyTaskDTO task){
        LegacyTaskDTO builtTask = buildTask(task);

        tasks.add(builtTask);
        log.info("Added task: {}", builtTask);
        return builtTask;
    }

    public List<LegacyTaskDTO> addTasks(List<LegacyTaskDTO> tasksList){
        for(LegacyTaskDTO task : tasksList){
            LegacyTaskDTO builtTask = buildTask(task);
            tasks.add(builtTask);
        }

        return tasks;
    }

    public void deleteAllTasks() {
        log.info("Deleting all tasks");
        tasks.clear();
    }

    public List<LegacyTaskDTO> deleteTaskById(Long id){
        log.info("Deleting task by id: {}", id);

        LegacyTaskDTO task = getTaskById(id);
        if(task != null) {
            tasks.remove(task);
        }
        return tasks;
    }



    public LegacyTaskDTO updateTaskById(Long id, LegacyTaskDTO task){
        log.info("Updating task by id: {}", id);
        LegacyTaskDTO builtTask = buildTask(task);
        LegacyTaskDTO myTask = getTaskById(id);
        if(myTask != null) {
            myTask.setDueDate(builtTask.getDueDate());
            myTask.setStatus(builtTask.getStatus());
            myTask.setContent(builtTask.getContent());
        }
        return myTask;
    }

    public LegacyTaskDTO updateTaskStatus(Long id, String status){
        log.info("Updating task status by id: {}", id);

        LegacyTaskDTO builtTask = getTaskById(id);

        if(builtTask != null) {
            builtTask.setStatus(status);
        }

        return builtTask;
    }

    private LegacyTaskDTO buildTask(LegacyTaskDTO task){
        return LegacyTaskDTO.builder()
                .id(task.getId())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .status((task.getStatus()))
                .build();
    }

}
