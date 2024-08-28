package com.example.finalproject.domain.campaign.dto;

import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import com.example.finalproject.domain.user.dto.UserDto;
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
public class CampaignPreferenceDto {

    private Integer seq;
    private UserDto userDto;
    private CampaignDto campaignDto;

    public static CampaignPreferenceDto of(Integer seq, UserDto userDto, CampaignDto campaignDto) {
        return new CampaignPreferenceDto(seq, userDto, campaignDto);
    }

    public static CampaignPreferenceDto from(CampaignPreference campaignPreference) {
        return CampaignPreferenceDto.of(
                campaignPreference.getSeq(),
                UserDto.from(campaignPreference.getUser()),
                CampaignDto.from(campaignPreference.getCampaign())
        );
    }
}
