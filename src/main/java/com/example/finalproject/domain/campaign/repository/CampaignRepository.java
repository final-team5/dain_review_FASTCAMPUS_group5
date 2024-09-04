package com.example.finalproject.domain.campaign.repository;

import com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Optional<Campaign> findByIdAndUserSeq(String id, Integer userSeq);
    Optional<Campaign> findById(String id);

    @Query(value =
        "SELECT new com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount(c, COUNT(ca)) " +
        "FROM Campaign c " +
        "LEFT JOIN CampaignPreference cp ON cp.campaign.seq = c.seq " +
        "LEFT JOIN CampaignApplicants ca ON ca.campaign.seq = c.seq " +
        "WHERE cp.user = :user AND c.status = :status " +
        "GROUP BY c"
    )
    Page<CampaignWithApplicantCount> findAllByUserAndStatus(
            @Param(value = "user") User user,
            @Param(value = "status") Integer status,
            Pageable pageable
    );

    default Campaign getCampaignBySeqOrException(Integer campaignSeq) {
        return findById(campaignSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.CAMPAIGN_NOT_FOUND)
        );
    }
}
