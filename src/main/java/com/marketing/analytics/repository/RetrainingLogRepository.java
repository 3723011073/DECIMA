package com.marketing.analytics.repository;

import com.marketing.analytics.entity.RetrainingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetrainingLogRepository extends JpaRepository<RetrainingLog, Long> {
    List<RetrainingLog> findAllByOrderByTrainingDateDesc();
}
