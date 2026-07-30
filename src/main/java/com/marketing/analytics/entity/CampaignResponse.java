package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private Boolean opened;

    @Column(nullable = false)
    private Boolean clicked;

    @Column(nullable = false)
    private Boolean responded;

    @Column(nullable = false)
    private Boolean conversion;

    @Column(nullable = false)
    private Double revenue;

    @Column(name = "response_date", nullable = false, updatable = false)
    private LocalDateTime responseDate;

    @PrePersist
    protected void onCreate() {
        responseDate = LocalDateTime.now();
    }
}
