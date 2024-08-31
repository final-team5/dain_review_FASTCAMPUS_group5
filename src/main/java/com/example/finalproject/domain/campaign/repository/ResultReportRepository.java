package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.ResultReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultReportRepository extends JpaRepository<ResultReport, Integer> {
    @Query("SELECT r FROM ResultReport r WHERE r.campaign.seq = :campaignId")
    ResultReport findByCampaignId(@Param("campaignId") Integer campaignId);
}