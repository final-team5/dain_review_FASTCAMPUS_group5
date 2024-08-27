package com.example.finalproject.domain.campaign.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignInsertRequest {

    private String applicationEndDate;
    private String applicationStartDate;
    private String campaignLink;
    private int categorySeq;
    private String city;
    private String contents;
    private String district;
    private String experienceEndDate;
    private String experienceEndTime;
    private String experienceStartDate;
    private String experienceStartTime;
    private boolean fridayAvailable;
    private boolean fridayUnavailable;
    private String image;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String location;
    private String message;
    private String mission;
    private boolean mondayAvailable;
    private boolean mondayUnavailable;
    private int platformSeq;
    private int point;
    private int recruiter;
    private boolean saturdayAvailable;
    private boolean saturdayUnavailable;
    private String service;
    private boolean sundayAvailable;
    private boolean sundayUnavailable;
    private boolean thursdayAvailable;
    private boolean thursdayUnavailable;
    private String title;
    private boolean tuesdayAvailable;
    private boolean tuesdayUnavailable;
    private int type;   // typeSeq
    private boolean wednesdayAvailable;
    private boolean wednesdayUnavailable;
}