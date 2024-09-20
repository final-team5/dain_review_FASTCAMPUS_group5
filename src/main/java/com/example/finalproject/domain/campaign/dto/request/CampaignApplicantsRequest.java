package com.example.finalproject.domain.campaign.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignApplicantsRequest {

    @ApiModelProperty(value = "사용자 번호", example = "6", required = true)
    private Integer userSeq;

    @ApiModelProperty(value = "체험단 번호", example = "8", required = true)
    private Integer campaignSeq;

    @ApiModelProperty(value = "신청 메시지", example = "체험단 신청 메시지입니다.", required = true)
    private String message;
}