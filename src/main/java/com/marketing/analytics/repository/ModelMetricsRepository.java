package com.marketing.analytics.repository;

import com.marketing.analytics.entity.ModelMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelMetricsRepository extends JpaRepository<ModelMetrics, Long> {
    Optional<ModelMetrics> findTopByModelNameOrderByEvaluationDateDesc(String modelName);
    List<ModelMetrics> findByModelNameOrderByEvaluationDateDesc(String modelName);
}
