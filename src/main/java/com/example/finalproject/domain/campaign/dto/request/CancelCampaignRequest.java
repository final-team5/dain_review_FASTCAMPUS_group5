package com.example.finalproject.domain.campaign.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelCampaignRequest {

    @ApiModelProperty(value = "사용자 번호", example = "6", required = true)
    private Integer userSeq;

    @ApiModelProperty(value = "체험단 번호", example = "8", required = true)
    private Integer campaignSeq;

    @ApiModelProperty(value = "취소 사유", example = "개인적인 사유로 인해 신청을 취소합니다.", required = true)
    private String reason;
}