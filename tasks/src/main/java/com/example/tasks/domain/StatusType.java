package com.example.tasks.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "status_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusType {
    @Id
    @Column(name = "STATUS_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String statusTypeId;

    @Column(name = "STATUS_NAME")
    private String statusName;

    @Column(name = "CREATED_BY", nullable = false)
    @Builder.Default
    private String createdBy = "SYSTEM";

    @Column(name = "CREATION_DATE", nullable = false)
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    @Builder.Default
    private LocalDate  lastUpdateDate = LocalDate.now();

    @Column(name = "LAST_UPDATED_BY", nullable = false)
    @Builder.Default
    private String lastUpdatedBy = "SYSTEM";

    @Column(name = "CREATED_BY_FULLNAME")
    private String createdByFullName;

}
