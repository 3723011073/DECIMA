package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByAgeBetween(Integer minAge, Integer maxAge);
    List<Customer> findByGender(String gender);
    List<Customer> findByIncomeBetween(Double minIncome, Double maxIncome);
}
