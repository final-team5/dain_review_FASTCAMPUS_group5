package kr.co.dain_review.be.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInsert {
    private Integer userSeq;
    private String title;
    private String contents;
    private String service;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String productLink;
    private String mission;
    private Integer recruiter;
    private String image;
    private Integer categorySeq;
    private Integer platformSeq;
    private Integer typeSeq;

    private String applicationStartDate;
    private String applicationEndDate;
    private String selection;
    private String experienceStartDate;
    private String experienceEndDate;
    private String deadline;
    private String experienceStartTime;
    private String experienceEndTime;

    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    private String city;
    private String district;
    private Integer point;
    private String location;

    private String message;
}
