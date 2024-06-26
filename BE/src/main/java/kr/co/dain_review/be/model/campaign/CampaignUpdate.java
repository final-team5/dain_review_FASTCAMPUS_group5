package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignUpdate {
    private Integer campaignSeq;

    private Integer userSeq;
    private Integer categorySeq;
    private Integer platformSeq;
    private Integer typeSeq;

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
    private Integer status;
    private String city;
    private String district;
    private Integer point;
    private String tag;

    private String applicationStartDate;
    private String applicationEndDate;
    private String experienceStartDate;
    private String experienceEndDate;
    private String experienceStartTime;
    private String experienceEndTime;

    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    private Boolean hashTag;
    private Boolean map;
    private Boolean link;
    private Integer characters;
    private Integer videoLength;
    private Integer pictureCount;
    private Boolean accountTag;
    private Boolean sound;
    private Boolean advertisingDisplay;

    private String message;
}
