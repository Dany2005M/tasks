package com.example.tasks.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDate;

@Entity
@Table(name = "TASKS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "STATUS_TYPE_ID")
    private StatusType statusType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "CREATED_BY")
    @Builder.Default
    private String createdBy = "SYSTEM";

    @Column(name = "CREATION_DATE", nullable = false)
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    @Builder.Default
    private LocalDate lastUpdateDate =  LocalDate.now();

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    @Builder.Default
    private String lastUpdatedBy = "SYSTEM";

    @Column(name = "CREATED_BY_FULLNAME")
    private String createdByFullName;
}
