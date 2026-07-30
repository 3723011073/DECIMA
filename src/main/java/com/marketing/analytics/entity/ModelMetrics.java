package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "model_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @Column(nullable = false, length = 100)
    private String modelName;

    @Column(nullable = false, length = 50)
    private String version;

    @Column(nullable = false)
    private Double accuracy;

    @Column(nullable = false)
    private Double precisionScore;

    @Column(nullable = false)
    private Double recallScore;

    @Column(nullable = false)
    private Double f1Score;

    @Column(nullable = false)
    private Double aucScore;

    @Column(name = "evaluation_date", nullable = false, updatable = false)
    private LocalDateTime evaluationDate;

    @PrePersist
    protected void onCreate() {
        evaluationDate = LocalDateTime.now();
    }
}
