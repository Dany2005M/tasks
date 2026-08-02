package com.example.tasks.service;

import com.example.tasks.domain.Task;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail, String firstName, List<Task> overdueTasks, List<Task> urgentTasks) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Daily Digest - Action Required on Your Tasks");

            StringBuilder overdueHtml = new StringBuilder();
            if(overdueTasks.isEmpty()){
                overdueHtml.append("<li><span style='color: #27ae60;'>✔ No overdue tasks! Great job!</span></li>");
            } else {
                for(Task task: overdueTasks){
                    overdueHtml.append("<li><strong>[").append(task.getStatusType().getStatusName()).append("]</strong> ")
                            .append(task.getName()).append("</li>");
                }
            }

            StringBuilder urgentHtml = new StringBuilder();
            if(urgentTasks.isEmpty()){
                urgentHtml.append("<li><span style='color: #7f8c8d;'>No tasks scheduled for today.</span></li>");
            } else {
                for(Task task: urgentTasks){
                    urgentHtml.append("<li><strong>[").append(task.getStatusType().getStatusName()).append("]</strong> ")
                            .append(task.getName()).append("</li>");
                }
            }
            String htmlContent = """
                    <html>
                    <body style="font-family: Arial, sans-serif; color: #333; line-height: 1.6; background-color: #f9f9f9; padding: 20px;">
                        <div style="max-width: 600px; margin: 0 auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
           
                            <h2 style="color: #2c3e50; margin-bottom: 5px;">Hello %s,</h2>
                            <p style="font-size: 16px; color: #555;">Here is the summary of your tasks for today. You have some items that require your attention!</p>

                            <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">

                            <h3 style="color: #e74c3c; margin-bottom: 10px;">🔴 Overdue Tasks (%d)</h3>
                            <ul style="padding-left: 20px; color: #555;">
                                %s
                            </ul>

                            <h3 style="color: #f39c12; margin-top: 25px; margin-bottom: 10px;">🟡 Due Today (%d)</h3>
                            <ul style="padding-left: 20px; color: #555;">
                                %s
                            </ul>

                            <div style="text-align: center; margin-top: 40px;">
                                <a href="http://localhost:4200/login" 
                                   style="background-color: #0d6efd; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 16px; display: inline-block;">
                                   Go to Dashboard
                                </a>
                            </div>

                            <p style="margin-top: 40px; font-size: 12px; color: #999; text-align: center;">
                                Have a great day,<br>
                                The Tasks Team
                            </p>
                        </div>
                    </body>
                    </html>
                    """.formatted(
                    firstName,
                    overdueTasks.size(), overdueHtml.toString(),
                    urgentTasks.size(), urgentHtml.toString()
            );

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            log.info("HTML Daily digest sent successfully to {}", toEmail);
        } catch (MessagingException e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}
