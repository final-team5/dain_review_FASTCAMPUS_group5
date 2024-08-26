package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Optional<Campaign> findByIdAndUserSeq(String id, Integer userSeq);
    Optional<Campaign> findById(String id);
}
