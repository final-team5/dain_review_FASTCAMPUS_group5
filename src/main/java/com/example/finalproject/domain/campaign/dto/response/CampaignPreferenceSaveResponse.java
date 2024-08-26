package com.example.finalproject.domain.campaign.dto.response;

import com.example.finalproject.domain.campaign.dto.CampaignPreferenceDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignPreferenceSaveResponse {

    private Integer seq;
    private Integer campaignSeq;
    private String campaignTitle;
    private Integer userSeq;
    private String username;

    public static CampaignPreferenceSaveResponse of(Integer seq, Integer campaignSeq, String campaignTitle, Integer userSeq, String username) {
        return new CampaignPreferenceSaveResponse(seq, campaignSeq, campaignTitle, userSeq, username);
    }

    public static CampaignPreferenceSaveResponse from(CampaignPreferenceDto campaignPreferenceDto) {
        return CampaignPreferenceSaveResponse.of(
                campaignPreferenceDto.getSeq(),
                campaignPreferenceDto.getCampaignDto().getSeq(),
                campaignPreferenceDto.getCampaignDto().getTitle(),
                campaignPreferenceDto.getUserDto().getSeq(),
                campaignPreferenceDto.getUserDto().getName()
        );
    }
}
