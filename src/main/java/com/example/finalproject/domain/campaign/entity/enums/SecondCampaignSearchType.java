package com.example.finalproject.domain.campaign.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecondCampaignSearchType {
    READY("대기"),
    APPROVE("승인"),
    REFUSAL("거부"),
    CANCEL("취소")
    ;

    private final String info;
}
