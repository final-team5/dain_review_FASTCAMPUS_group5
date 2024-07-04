package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignInsert {
    private String title;
    private String contents;
    private String service;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String campaignLink;
    private String mission;
    private Integer recruiter;
    private String image;
    private Integer categorySeq;
    private Integer platformSeq;
    private Integer typeSeq;

    private String applicationStartDate;
    private String applicationEndDate;
    private String experienceStartDate;
    private String experienceEndDate;
    private String experienceStartTime;
    private String experienceEndTime;

    private Boolean mondayAvailable;
    private Boolean tuesdayAvailable;
    private Boolean wednesdayAvailable;
    private Boolean thursdayAvailable;
    private Boolean fridayAvailable;
    private Boolean saturdayAvailable;
    private Boolean sundayAvailable;

    private Boolean mondayUnavailable;
    private Boolean tuesdayUnavailable;
    private Boolean wednesdayUnavailable;
    private Boolean thursdayUnavailable;
    private Boolean fridayUnavailable;
    private Boolean saturdayUnavailable;
    private Boolean sundayUnavailable;

    private String city;
    private String district;
    private Integer point;
    private String location;

    private String message;
}
