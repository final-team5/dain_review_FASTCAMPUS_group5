package com.example.finalproject.domain.campaign.dto.response;

import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.campaign.entity.enums.SecondCampaignSearchType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignListResponse {

    private Integer seq;
    private String title;
    private String segment;
    private Integer point;
    private Integer recruiter;
    private String email;
    private String companyName;
    private String platform;
    private String type;
    private String phone;
    private String registeredAt;
    private String updatedAt;
    private String status;


    public static CampaignListResponse of(Integer seq, String title, String segment, Integer point, Integer recruiter,
                                          String email, String companyName, String platform, String type, String phone,
                                          String registeredAt, String updatedAt, String status) {
        return new CampaignListResponse(seq, title, segment, point, recruiter, email, companyName, platform, type, phone, registeredAt, updatedAt, status);
    }

    public static CampaignListResponse from(CampaignDto campaignDto) {
        String registeredAt = campaignDto.getRegisteredAt().toString().replace('T', ' ');
        String updatedAt = campaignDto.getUpdatedAt().toString().replace('T', ' ');

        return CampaignListResponse.of(
                campaignDto.getSeq(),
                campaignDto.getTitle(),
                campaignDto.getSegment(),
                campaignDto.getPoint(),
                campaignDto.getRecruiter(),
                campaignDto.getUserDto().getEmail(),
                campaignDto.getTitle(),
                campaignDto.getPlatform(),
                campaignDto.getType(),
                campaignDto.getUserDto().getPhone(),
                registeredAt,
                updatedAt,
                SecondCampaignSearchType.fromCode(campaignDto.getStatus()).getInfo()
        );
    }
}
