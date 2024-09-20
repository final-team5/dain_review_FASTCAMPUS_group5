package com.example.finalproject.domain.campaign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignApplicantsRequest {
    private Integer userSeq;
    private Integer campaignSeq;
    private String message;
}