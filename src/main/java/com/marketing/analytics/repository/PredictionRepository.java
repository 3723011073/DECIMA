package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Customer;
import com.marketing.analytics.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByCustomer(Customer customer);
    List<Prediction> findByModelVersion(String modelVersion);
    List<Prediction> findByRiskCategory(String riskCategory);
}
