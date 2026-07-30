package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_insights")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIInsight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long insightId;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String insightText;

    @Column(nullable = false)
    private Double confidence;

    @Column(name = "generated_date", nullable = false, updatable = false)
    private LocalDateTime generatedDate;

    @PrePersist
    protected void onCreate() {
        generatedDate = LocalDateTime.now();
    }
}
