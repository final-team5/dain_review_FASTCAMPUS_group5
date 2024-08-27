package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Optional<Campaign> findByIdAndUserSeq(String id, Integer userSeq);
    Optional<Campaign> findById(String id);

    default Campaign getCampaignBySeqOrException(Integer campaignSeq) {
        return findById(campaignSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.CAMPAIGN_NOT_FOUND)
        );
    }
}
