package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.api.client.util.DateTime;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Campaign {
    private Integer id;
    private String userSeq;
    private String category;
    private String platform;
    private String type;
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
    private String city;
    private String district;
    private Integer point;
    private String tag;
    private Integer status;
    private Date applicationStartDate;
    private Date applicationEndDate;
    private Date experienceStartDate;
    private Date experienceEndDate;
    private DateTime experienceStartTime;
    private DateTime experienceDndTime;

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

    private Boolean hashTag;
    private Boolean map;
    private Boolean link;
    private Integer characters;
    private Integer videoLength;
    private Integer pictureCount;
    private Boolean accountTag;
    private Boolean sound;
    private Boolean isAdvertising;
    private Boolean isKftc;
}
