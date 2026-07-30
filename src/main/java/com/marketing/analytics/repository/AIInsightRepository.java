package com.marketing.analytics.repository;

import com.marketing.analytics.entity.AIInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIInsightRepository extends JpaRepository<AIInsight, Long> {
    List<AIInsight> findByCategory(String category);
    List<AIInsight> findByOrderByGeneratedDateDesc();
}
