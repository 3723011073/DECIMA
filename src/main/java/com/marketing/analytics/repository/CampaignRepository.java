package com.marketing.analytics.repository;

import com.marketing.analytics.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(Campaign.CampaignStatus status);
    List<Campaign> findByChannel(String channel);
    List<Campaign> findByCampaignType(String campaignType);
}
