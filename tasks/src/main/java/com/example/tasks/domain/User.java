package com.example.tasks.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "IS_INTERNAL")
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    @Builder.Default
    private Boolean isInternal = true;

    @Column(name = "CREATED_BY", nullable = false)
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
