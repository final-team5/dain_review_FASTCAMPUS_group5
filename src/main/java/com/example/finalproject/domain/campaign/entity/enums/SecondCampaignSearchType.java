package com.example.finalproject.domain.campaign.entity.enums;

import com.example.finalproject.domain.campaign.entity.CampaignStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecondCampaignSearchType {
    READY(2, "대기"),
    APPROVE(3, "승인"),
    REFUSAL(6, "거부"),
    CANCEL(5, "취소")
    ;

    private final Integer code;
    private final String info;

    public static SecondCampaignSearchType fromCode(int code) {
        for (SecondCampaignSearchType status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code for SecondCampaignSearchType: " + code);
    }
}
