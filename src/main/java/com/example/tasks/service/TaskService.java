package com.example.tasks.service;


import com.example.tasks.dto.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private List<TaskDTO> tasks = new ArrayList<>();

    public List<TaskDTO> getTasks() {
        log.info("Getting tasks: ");

        return tasks;
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Getting task by id: {}", id);
        return tasks.stream()
                .filter(taskDTO -> taskDTO.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<TaskDTO> getTasksSortedByDueDate() {
        log.info("Getting tasks sorted by due date...");

        return tasks.stream()
                .sorted(Comparator.comparing(TaskDTO::getDueDate))
                .toList();
    }

    public List<TaskDTO> getTasksByStatus(String status) {
        log.info("Getting tasks by status: {}", status);

        return tasks.stream()
                .filter(task -> task.getStatus().equalsIgnoreCase(status))
                .toList();
    }


    public List<TaskDTO> searchTasksByKeyword(String keyword) {
        log.info("Searching tasks by keyword: {}", keyword);

        return tasks.stream()
                .filter(task -> task.getContent()!= null && task.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }


    public TaskDTO addTask(TaskDTO task){
        TaskDTO builtTask = buildTask(task);

        tasks.add(builtTask);
        log.info("Added task: {}", builtTask);
        return builtTask;
    }

    public void deleteAllTasks() {
        log.info("Deleting all tasks");
        tasks.clear();
    }

    public List<TaskDTO> deleteTaskById(Long id){
        log.info("Deleting task by id: {}", id);

        TaskDTO task = getTaskById(id);
        if(task != null) {
            tasks.remove(task);
        }
        return tasks;
    }



    public TaskDTO updateTaskById(Long id, TaskDTO task){
        log.info("Updating task by id: {}", id);
        TaskDTO builtTask = buildTask(task);
        TaskDTO myTask = getTaskById(id);
        if(myTask != null) {
            myTask.setDueDate(builtTask.getDueDate());
            myTask.setStatus(builtTask.getStatus());
            myTask.setContent(builtTask.getContent());
        }
        return myTask;
    }

    public TaskDTO updateTaskStatus(Long id, String status){
        log.info("Updating task status by id: {}", id);

        TaskDTO builtTask = getTaskById(id);

        if(builtTask != null) {
            builtTask.setStatus(status);
        }

        return builtTask;
    }

    private  TaskDTO buildTask(TaskDTO task){
        return TaskDTO.builder()
                .id(task.getId())
                .content(task.getContent())
                .dueDate(task.getDueDate())
                .status((task.getStatus()))
                .build();
    }

}
