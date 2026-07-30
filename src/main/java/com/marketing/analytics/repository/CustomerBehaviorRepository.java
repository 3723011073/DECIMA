package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Customer;
import com.marketing.analytics.entity.CustomerBehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerBehaviorRepository extends JpaRepository<CustomerBehavior, Long> {
    Optional<CustomerBehavior> findByCustomer(Customer customer);
}
