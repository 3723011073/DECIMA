package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Campaign;
import com.marketing.analytics.entity.CampaignOutcome;
import com.marketing.analytics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignOutcomeRepository extends JpaRepository<CampaignOutcome, Long> {
    List<CampaignOutcome> findByCampaign(Campaign campaign);
    List<CampaignOutcome> findByCustomer(Customer customer);
    List<CampaignOutcome> findByCampaignAndActualConversionTrue(Campaign campaign);
}
