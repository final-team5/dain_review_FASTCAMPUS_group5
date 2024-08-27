package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampaignPreferenceRepository extends JpaRepository<CampaignPreference, Integer> {

    Optional<CampaignPreference> findByCampaign(Campaign campaign);

    default CampaignPreference getCampaignPreferenceByCampaignOrException(Campaign campaign) {
        return findByCampaign(campaign).orElseThrow(
                () -> new ValidException(ValidErrorCode.CAMPAIGN_PREFERENCE_NOT_FOUND)
        );
    }
}
