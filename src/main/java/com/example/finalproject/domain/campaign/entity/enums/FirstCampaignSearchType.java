package com.example.finalproject.domain.campaign.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FirstCampaignSearchType {
    ALL("전체"),
    CAMPAIGN_TITLE("체험단명"),
    ID("아이디"),
    COMPANY_NAME("업체명"),
    PHONE_NUMBER("휴대전화 번호")
    ;

    private final String info;
}
