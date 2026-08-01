package com.example.tasks.repository;

import com.example.tasks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIsInternal(Boolean isInternal);
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN Task t ON u.userId = t.user.userId AND t.statusType.statusName NOT IN ('Done', 'Cancelled') " +
            "GROUP BY u " +
            "ORDER BY COUNT(t.taskId) ASC")
    List<User> findUsersOrderedByTaskCount();
}
