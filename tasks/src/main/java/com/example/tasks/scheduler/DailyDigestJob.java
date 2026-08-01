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

    @Scheduled(fixedRate = 60000)
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
                long dueTodayCount = actionableTasks.stream()
                        .filter(task -> task.getDueDate().isEqual(today))
                        .count();
                long overdueCount = actionableTasks.stream()
                        .filter(task -> task.getDueDate().isBefore(today))
                        .count();

                StringBuilder emailBody = new StringBuilder();
                emailBody.append("Hello  ").append(user.getUsername()).append(",\n\n");
                emailBody.append("Here is your daily digest:\n\n");
                emailBody.append("- Tasks with due date TODAY: ").append(dueTodayCount).append("\n");
                emailBody.append("- Overdue Tasks: ").append(overdueCount).append("\n\n");

                for(Task task: actionableTasks) {
                    emailBody.append(" • [").append(task.getStatusType().getStatusName()).append("] ").append(task.getName()).append("\n");
                }

                emailBody.append("\nPlease enter on the platform to view them. \nHave a great day!");
                try {
                    emailService.sendSimpleEmail(user.getEmail(), "Daily digest - Your tasks", emailBody.toString());
                    log.info("Digest sent successfully to: {}", user.getEmail());
                } catch (Exception e) {
                    log.error("Failed to send daily tasks email to: {}", user.getEmail(), e);
                }
            }
        }
        log.info("Finished DailyDigestJob");
    }


}
