package com.example.finalproject.domain.user.dto.response;

import com.example.finalproject.domain.campaign.dto.response.CampaignPreferenceListResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesMyPageResponse {

    private Integer seq;
    private String role;
    private String companyName;
    private String profileUrl;
    private Integer point;

    private Integer progressCounts;

    private Page<CampaignPreferenceListResponse> campaignListResponse;

    public static BusinessesMyPageResponse of(Integer seq, String role, String companyName, String profileUrl,
                                              Integer point, Integer progressCounts, Page<CampaignPreferenceListResponse> campaignListResponse) {
        return new BusinessesMyPageResponse(seq, role, companyName, profileUrl, point, progressCounts, campaignListResponse);
    }
}
