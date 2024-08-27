package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignPreferenceRepository extends JpaRepository<CampaignPreference, Integer> {
}
