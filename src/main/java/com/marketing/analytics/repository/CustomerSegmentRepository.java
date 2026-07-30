package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Customer;
import com.marketing.analytics.entity.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Long> {
    Optional<CustomerSegment> findByCustomer(Customer customer);
    List<CustomerSegment> findBySegmentName(String segmentName);
    List<CustomerSegment> findByRfmScore(String rfmScore);
}
