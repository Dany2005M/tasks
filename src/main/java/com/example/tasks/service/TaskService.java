package com.example.tasks.service;


import com.example.tasks.dto.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .filter(taskDTO -> taskDTO.getId() == id)
                .findFirst()
                .orElse(null);
    }



    public List<TaskDTO> addTask(TaskDTO task){
        TaskDTO builtTask = buildTask(task);

        tasks.add(builtTask);
        log.info("Added task: {}", builtTask);
        return tasks;
    }

    public List<TaskDTO> deleteTaskById(Long id){
        log.info("Deleting task by id: {}", id);
        if(getTaskById(id) != null) {
            tasks.remove(getTaskById(id));
        }
        return tasks;
    }

    public TaskDTO updateTaskById(Long id, TaskDTO task){
        log.info("Updating task by id: {}", id);
        TaskDTO builtTask = buildTask(task);
        TaskDTO myTask = getTaskById(id);
        if(myTask != null) {
            myTask.setId(builtTask.getId());
            myTask.setDueDate(builtTask.getDueDate());
            myTask.setStatus(builtTask.getStatus());
            myTask.setContent(builtTask.getContent());
        }
        return task;
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
