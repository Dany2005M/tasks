package com.example.tasks.config;

import com.example.tasks.domain.User;
import com.example.tasks.repository.UserRepository;
import com.example.tasks.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("permissions_checker")
@RequiredArgsConstructor
public class PermissionChecker {
    private final TaskService taskService;
    private final UserRepository userRepository;

    public boolean hasPermission(String resource, String action) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        if (auth == null || !auth.isAuthenticated()) {
            return false;
        }

        String email = auth.getName();

        User user = userRepository.findByEmail(email).orElse(null);

        if(user == null || user.getRole() == null) {
            return false;
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole().getRoleName())) {
            return true;
        }

        return user.getRole().getPermissions().stream()
                .anyMatch(p -> p.getPermissionAction().equalsIgnoreCase(action)
                        && p.getPermissionResource().equalsIgnoreCase(resource));
    }
}
