package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.CampaignApplicants;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignApplicantsRepository extends JpaRepository<CampaignApplicants, Integer> {

    boolean existsByUserAndCampaign(User user, Campaign campaign);

    Optional<CampaignApplicants> findByUserAndCampaignSeq(Integer userSeq, Integer campaignSeq);
}
