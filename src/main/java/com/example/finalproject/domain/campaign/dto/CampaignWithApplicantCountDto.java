package com.example.finalproject.domain.campaign.dto;

import com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignWithApplicantCountDto {

    private CampaignDto campaignDto;
    private Long applicantCount;

    public static CampaignWithApplicantCountDto of(CampaignDto campaignDto, Long applicantCount) {
        return new CampaignWithApplicantCountDto(campaignDto, applicantCount);
    }

    public static CampaignWithApplicantCountDto from(CampaignWithApplicantCount campaignWithApplicantCount) {
        return CampaignWithApplicantCountDto.of(
                CampaignDto.from(campaignWithApplicantCount.getCampaign()),
                campaignWithApplicantCount.getApplicantCount()
        );
    }
}
