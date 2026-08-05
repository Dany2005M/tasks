package com.example.tasks.scheduler;

import com.example.tasks.domain.Task;
import com.example.tasks.domain.User;
import com.example.tasks.repository.TaskRepository;
import com.example.tasks.repository.UserRepository;
import com.example.tasks.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyDigestJob {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final EmailService emailService;

//    @Scheduled(fixedRate = 60000)
    public void generateAndSendDailyDigest() {
        log.info("Starting DailyDigestJob");

        LocalDate today = LocalDate.now();
        List<User> allUsers = userRepository.findAll();

        for(User user : allUsers) {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                continue;
            }

            List<Task> actionableTasks = taskRepository.findActionableTasksForUser(user.getUserId());

            if(!actionableTasks.isEmpty()) {
                List<Task> urgentTasks = actionableTasks.stream()
                        .filter(task -> task.getDueDate().isEqual(today))
                        .toList();
                List<Task> overdueTasks = actionableTasks.stream()
                        .filter(task -> task.getDueDate().isBefore(today))
                        .toList();

                try {
                    emailService.sendEmail(
                            user.getEmail(),
                            user.getUsername(),
                            overdueTasks,
                            urgentTasks
                    );
                    log.info("DailyDigestJob has been sent to user {}", user.getEmail());
                } catch (Exception e) {
                    log.error("Failed to send DailyDigestJob email to user {}", user.getEmail(), e);
                }
            }
        }
        log.info("Finished DailyDigestJob");
    }


}
