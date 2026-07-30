package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "retraining_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetrainingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false, length = 50)
    private String oldModelVersion;

    @Column(nullable = false, length = 50)
    private String newModelVersion;

    @Column(nullable = false)
    private Double oldAccuracy;

    @Column(nullable = false)
    private Double newAccuracy;

    @Column(name = "training_date", nullable = false, updatable = false)
    private LocalDateTime trainingDate;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @PrePersist
    protected void onCreate() {
        trainingDate = LocalDateTime.now();
    }
}
