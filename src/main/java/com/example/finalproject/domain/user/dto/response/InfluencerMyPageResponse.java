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
public class InfluencerMyPageResponse {

    private Integer seq;
    private String role;
    private String nickname;
    private String profileUrl;
    private String instagram;
    private String blog;
    private String youtube;
    private String tiktok;
    private Integer point;

    private Integer applicationCounts;
    private Integer selectedCounts;
    private Integer progressCounts;

    private Page<CampaignPreferenceListResponse> campaignListResponse;

    public static InfluencerMyPageResponse of(Integer seq, String role, String nickname, String profileUrl,
                                              String instagram, String blog, String youtube, String tiktok,
                                              Integer point, Integer applicationCounts, Integer selectedCounts,
                                              Integer progressCounts, Page<CampaignPreferenceListResponse> campaignListResponse) {
        return new InfluencerMyPageResponse(seq, role, nickname, profileUrl, instagram, blog, youtube, tiktok,
                point, applicationCounts, selectedCounts, progressCounts, campaignListResponse);
    }
}
