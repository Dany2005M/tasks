package com.example.tasks.repository;

import com.example.tasks.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser_UserId(Long id, Pageable pageable);
    List<Task> findByOrderByDueDateAsc();
    List<Task> findByStatusType_StatusName(String statusName);
    List<Task> findByDueDateBefore(LocalDate dueDate);

    @Query("SELECT st.statusName, COUNT(t) FROM Task t JOIN t.statusType st GROUP BY st.statusName")
    List<Object[]> countTasksGroupedByStatus();

    @Query("SELECT t from Task t WHERE " +
            "(:subject IS NULL OR LOWER(t.name) LIKE " +
            "LOWER(CONCAT('%', :subject, '%'))) AND (:assignedTo IS NULL OR t.user.username = :assignedTo)" +
            "AND (CAST(:dueDate AS date) IS NULL OR t.dueDate = :dueDate)" +
            "AND (:status IS NULL OR t.statusType.statusName = :status)")
    Page<Task> searchTasks(@Param("subject") String subject,
                           @Param("assignedTo") String assignedTo,
                           @Param("dueDate") LocalDate dueDate,
                           @Param("status") String status,
                           Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.user.userId = :userId " +
            "AND t.statusType.statusName NOT IN ('Done', 'Cancelled') ")
    List<Task> findActionableTasksForUser(@Param("userId") Long userId);


}
