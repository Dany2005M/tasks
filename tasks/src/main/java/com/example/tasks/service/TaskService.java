package com.example.tasks.service;

import com.example.tasks.domain.StatusType;
import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.dto.TaskDTO;
import com.example.tasks.mapper.TaskMapper;
import com.example.tasks.repository.StatusTypeRepository;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusTypeRepository statusTypeRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public Page<TaskDTO> getAllTasks(int page, int size, String sortBy, String sortDirection) {
        log.info("Tasks retrieved!");

        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User loggedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        String convertedSortField = switch(sortBy.toLowerCase()) {
            case "user" -> "user.username";
            case "name" -> "name";
            case "status" -> "statusType.statusName";
            case "date" -> "dueDate";
            default -> "taskId";
        };

        Sort sort = sortDirection.equals(Sort.Direction.ASC.name()) ?
                Sort.by(convertedSortField).ascending() : Sort.by(convertedSortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage;

        if(loggedUser.getRole() != null && "ADMIN".equalsIgnoreCase(loggedUser.getRole().getRoleName())){
            taskPage = taskRepository.findAll(pageable);
        }
        else{
            taskPage = taskRepository.findByUser_UserId(loggedUser.getUserId(), pageable);
        }

        return taskPage.map(taskMapper::toDTO);
    }

    public TaskDTO getTaskById(Long id) {
        log.info("Task with id {} retrieved!", id);

        return taskRepository.findById(id)
                .map(taskMapper::toDTO)
                .orElse(null);
    }

    public Page<TaskDTO> searchTasks(String subject, String assignedTo, LocalDate dueDate, String status, int page, int size, String sortBy, String sortDirection) {

        String convertedSortField = switch(sortBy.toLowerCase()) {
            case "user" -> "user.username";
            case "name" -> "name";
            case "status" -> "statusType.statusName";
            case "date" -> "dueDate";
            default -> "taskId";
        };

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(convertedSortField).ascending() : Sort.by(convertedSortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Task> taskPage = taskRepository.searchTasks(subject, assignedTo, dueDate, status, pageable);

        return taskPage.map(taskMapper::toDTO);
    }

    public List<TaskDTO> getTasksSortedByDueDate() {
        log.info("Tasks retrieved sorted by due date!");

        return taskRepository.findByOrderByDueDateAsc()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTasksByStatusName(String statusName) {
        log.info("Tasks retrieved by status name {} retrieved!", statusName);

        return taskRepository.findByStatusType_StatusName(statusName)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTasksEarlierThan(LocalDate date){
        log.info("Getting tasks due date earlier than date: {}", date);

        return taskRepository.findByDueDateBefore(date)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public Map<String, Long> getTaskCountGroupedByStatus()
    {
        log.info("Task count grouped by status retrieved!");

        return taskRepository.countTasksGroupedByStatus()
                .stream()
                .collect(Collectors.toMap(
                        row -> (String)row[0], row -> (Long)row[1]
                ));
    }

    public TaskDTO updateTaskStatus(Long taskId, String statusId) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User currentUser = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found!"));

        Task task = taskRepository.findById(taskId)
                        .orElseThrow(() -> new RuntimeException("Task with id " + taskId + " not found!"));
        StatusType newStatus = statusTypeRepository.findById(statusId)
                        .orElseThrow(() -> new RuntimeException("StatusType with id " + statusId + " not found!"));

        boolean isAdmin = currentUser.getRole() != null && "ADMIN".equalsIgnoreCase(currentUser.getRole().getRoleName());

        if(!isAdmin){
            List<String> allowedStatuses = List.of("Pending", "In Progress", "Review");

            if(!allowedStatuses.contains(newStatus.getStatusName())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "You do not have permission to set this status. You can only use: Pending, In Progress, Review.\n");
            }

        }

        log.info("Task status with id {} updated!", taskId);

        task.setStatusType(newStatus);
        taskRepository.save(task);

        return taskMapper.toDTO(task);
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        log.info("Task created!");

        Task task = taskMapper.toEntity(taskDTO);

        if(taskDTO.getUserId() == null){
            List<User> users = userRepository.findUsersOrderedByTaskCount();

            if(!users.isEmpty()){
                User leastBusyUser = users.getFirst();
                task.setUser(leastBusyUser);
            }

        }
        else{
            userRepository.findById(taskDTO.getUserId()).ifPresent(task::setUser);
        }
        Task savedTask = taskRepository.save(task);

        return taskMapper.toDTO(savedTask);
    }

    public List<TaskDTO> createTasks(List<TaskDTO> taskDTOs) {
        log.info("Tasks created!");

        List<Task> tasks = taskDTOs.stream()
                .map(taskMapper::toEntity)
                .toList();

        List<Task> savedTasks = taskRepository.saveAll(tasks);
        return savedTasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        log.info("Task with id {} updated!", id);

        Task existingTask = taskRepository.findById(id)
                .orElse(null);

        Task savedTask = null;

        if(existingTask != null) {
            existingTask.setName(taskDTO.getName());
            existingTask.setDueDate(taskDTO.getDueDate());
            existingTask.setStatusType(statusTypeRepository.getReferenceById(taskDTO.getStatusTypeId()));
            existingTask.setUser(userRepository.getReferenceById(taskDTO.getUserId()));
            existingTask.setCreatedByFullName(taskDTO.getCreatedByFullName());

            savedTask = taskRepository.save(existingTask);
        }

        return taskMapper.toDTO(savedTask);
    }

    public void deleteAllTasks() {
        log.info("Tasks deleted!");

        taskRepository.deleteAll();
    }

    public void deleteTaskById(Long id) {
        log.info("Task with id {} deleted!", id);
        taskRepository.deleteById(id);

    }



}
