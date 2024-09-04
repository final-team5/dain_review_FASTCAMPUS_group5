package com.example.finalproject.domain.campaign.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "체험단 신규 모집에 필요한 요청 정보")
public class CampaignInsertRequest {

    @ApiModelProperty(value = "모집 마감일", required = true, example = "2024-09-20")
    private String applicationEndDate;

    @ApiModelProperty(value = "모집 시작일", required = true, example = "2024-09-10")
    private String applicationStartDate;

    @ApiModelProperty(value = "상품 URL", required = true, example = "https://example.com/campaign")
    private String campaignLink;

    @ApiModelProperty(value = "카테고리", required = true, example = "맛집")
    private int category;

    @ApiModelProperty(value = "도시 분류", required = true, example = "서울")
    private String city;

    @ApiModelProperty(value = "내용", required = true, example = "체험단에 대한 상세 내용입니다.")
    private String contents;

    @ApiModelProperty(value = "구", required = true, example = "강남구")
    private String district;

    @ApiModelProperty(value = "체험 마감일", required = true, example = "2024-10-15")
    private String experienceEndDate;

    @ApiModelProperty(value = "체험 가능 마감 시간", required = false, example = "18:00")
    private String experienceEndTime;

    @ApiModelProperty(value = "체험 시작일", required = true, example = "2024-10-01")
    private String experienceStartDate;

    @ApiModelProperty(value = "체험 가능 시작 시간", required = false, example = "10:00")
    private String experienceStartTime;

    @ApiModelProperty(value = "금요일 가능 여부", required = false, example = "true")
    private boolean friday;

    @ApiModelProperty(value = "보여지는 이미지 URL", required = false, example = "https://example.com/image.png")
    private String image;

    @ApiModelProperty(value = "추가할 키워드 1", required = false, example = "인천휴가")
    private String keyword1;

    @ApiModelProperty(value = "추가할 키워드 2", required = false, example = "인천행사")
    private String keyword2;

    @ApiModelProperty(value = "추가할 키워드 3", required = false, example = "인천이벤트")
    private String keyword3;

    @ApiModelProperty(value = "기본 주소", required = false, example = "인천광역시 연수구 센트럴로 313")
    private String address;

    @ApiModelProperty(value = "상세 주소", required = false, example = "다인기획")
    private String addressDetail;

    @ApiModelProperty(value = "미션", required = false, example = "리뷰 작성하기")
    private String mission;

    @ApiModelProperty(value = "월요일 가능 여부", required = false, example = "true")
    private boolean monday;

    @ApiModelProperty(value = "플랫폼", required = true, example = "블로그")
    private String platform;

    @ApiModelProperty(value = "포인트", required = false, example = "5000")
    private int point;

    @ApiModelProperty(value = "모집 인수", required = true, example = "10")
    private int recruiter;

    @ApiModelProperty(value = "토요일 가능 여부", required = false, example = "true")
    private boolean saturday;

    @ApiModelProperty(value = "제공 서비스/물품", required = false, example = "당일 숙박권")
    private String service;

    @ApiModelProperty(value = "일요일 가능 여부", required = false, example = "true")
    private boolean sunday;

    @ApiModelProperty(value = "목요일 가능 여부", required = false, example = "true")
    private boolean thursday;

    @ApiModelProperty(value = "제목", required = true, example = "신제품 체험단 모집")
    private String title;

    @ApiModelProperty(value = "화요일 가능 여부", required = false, example = "true")
    private boolean tuesday;

    @ApiModelProperty(value = "체험단 유형", required = true, example = "방문형")
    private String type;

    @ApiModelProperty(value = "체험단 종류", required = true, example = "프리미엄")
    private String segment;

    @ApiModelProperty(value = "수요일 가능 여부", required = false, example = "true")
    private boolean wednesday;
}