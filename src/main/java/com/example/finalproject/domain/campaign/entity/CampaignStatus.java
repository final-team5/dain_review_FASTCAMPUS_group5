package com.example.finalproject.domain.campaign.entity;

public enum CampaignStatus {
    DRAFT(1, "초안"),
    READY(2, "대기"),
    ACTIVE(3, "진행"),
    COMPLETE(4, "완료"),
    CANCELED(5, "취소"),
    DELETED(6, "삭제"),
    NONE(0, "없음");

    private final int code;
    private final String description;

    CampaignStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CampaignStatus fromCode(int code) {
        for (CampaignStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code for CampaignStatus: " + code);
    }
}