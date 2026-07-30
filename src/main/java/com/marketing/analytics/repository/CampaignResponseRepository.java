package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Campaign;
import com.marketing.analytics.entity.CampaignResponse;
import com.marketing.analytics.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignResponseRepository extends JpaRepository<CampaignResponse, Long> {
    List<CampaignResponse> findByCampaign(Campaign campaign);
    List<CampaignResponse> findByCustomer(Customer customer);
    List<CampaignResponse> findByCampaignAndConversionTrue(Campaign campaign);
}
