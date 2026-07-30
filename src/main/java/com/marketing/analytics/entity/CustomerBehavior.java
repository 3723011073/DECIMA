package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_behavior")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerBehavior {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long behaviorId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer websiteVisits;

    @Column(nullable = false)
    private Integer purchaseCount;

    @Column(name = "last_purchase_date")
    private LocalDateTime lastPurchaseDate;

    @Column(nullable = false)
    private Double totalSpending;

    @Column(nullable = false)
    private Double engagementScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
