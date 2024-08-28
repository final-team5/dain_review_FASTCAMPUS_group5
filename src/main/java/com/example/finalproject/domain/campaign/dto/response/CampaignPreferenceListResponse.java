package com.example.finalproject.domain.campaign.dto.response;

import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.campaign.dto.CampaignWithApplicantCountDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignPreferenceListResponse {

    private Integer seq;
    private String image;
    private String city;
    private String district;
    private Integer status;
    private String title;
    private String service;
    private String platform;
    private String type;
    private String category;
    private Integer recruiter;
    private Long applicantsCount;
    private Date applicationEndDate;
    private String tag;

    public static CampaignPreferenceListResponse of(Integer seq, String image, String city, String district, Integer status, String title, String service, String platform, String type,
                                                    String category, Integer recruiter, Long applicantsCount, Date applicationEndDate, String tag) {
        return new CampaignPreferenceListResponse(seq, image, city, district, status, title, service, platform, type,
                category, recruiter, applicantsCount, applicationEndDate, tag);
    }

    public static CampaignPreferenceListResponse from(CampaignWithApplicantCountDto campaignWithApplicantCountDto) {
        CampaignDto campaignDto = campaignWithApplicantCountDto.getCampaignDto();

        return CampaignPreferenceListResponse.of(
                campaignDto.getSeq(),
                campaignDto.getImage(),
                campaignDto.getCity(),
                campaignDto.getDistrict(),
                campaignDto.getStatus(),
                campaignDto.getTitle(),
                campaignDto.getService(),
                campaignDto.getPlatform(),
                campaignDto.getType(),
                campaignDto.getCategory(),
                campaignDto.getRecruiter(),
                campaignWithApplicantCountDto.getApplicantCount(),
                campaignDto.getApplicationEndDate(),
                campaignDto.getTag()
        );
    }
}
