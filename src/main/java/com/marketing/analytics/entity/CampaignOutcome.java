package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_outcomes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignOutcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outcomeId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "prediction_id", nullable = true)
    private Prediction prediction;

    @Column(nullable = false)
    private Boolean actualConversion;

    @Column(nullable = false)
    private Double actualRevenue;

    @Column(nullable = false, length = 100)
    private String campaignResult;

    @Column(name = "outcome_date", nullable = false, updatable = false)
    private LocalDateTime outcomeDate;

    @PrePersist
    protected void onCreate() {
        outcomeDate = LocalDateTime.now();
    }
}
