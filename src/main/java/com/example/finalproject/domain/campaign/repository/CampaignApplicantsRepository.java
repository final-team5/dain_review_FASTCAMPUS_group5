package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.CampaignApplicants;
import com.example.finalproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignApplicantsRepository extends JpaRepository<CampaignApplicants, Integer> {
    Integer countByUser(User user);

    Integer countByUserAndApplication(User user, Integer application);
}
