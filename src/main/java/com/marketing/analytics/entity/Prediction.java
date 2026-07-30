package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long predictionId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 50)
    private String modelVersion;

    @Column(nullable = false)
    private Double probability;

    @Column(nullable = false)
    private Double confidenceScore;

    @Column(nullable = false)
    private Double riskScore;

    @Column(nullable = false, length = 50)
    private String riskCategory;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recommendedAction;

    @Column(name = "prediction_date", nullable = false, updatable = false)
    private LocalDateTime predictionDate;

    @PrePersist
    protected void onCreate() {
        predictionDate = LocalDateTime.now();
    }
}
