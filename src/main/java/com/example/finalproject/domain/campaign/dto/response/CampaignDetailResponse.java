package com.example.finalproject.domain.campaign.dto.response;

import com.example.finalproject.domain.user.dto.response.InfluencerDetailResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "체험단 상세 내용")
public class CampaignDetailResponse {

    @ApiModelProperty(value = "체험단 고유 ID", example = "1")
    private Integer seq;

    @ApiModelProperty(value = "제목", example = "신제품 체험단 모집")
    private String title;

    @ApiModelProperty(value = "내용", example = "체험단에 대한 상세 내용입니다.")
    private String contents;

    @ApiModelProperty(value = "상태", example = "1")
    private Integer status;

    @ApiModelProperty(value = "모집 시작일", example = "2024-09-10")
    private Date applicationStartDate;

    @ApiModelProperty(value = "모집 마감일", example = "2024-09-20")
    private Date applicationEndDate;

    @ApiModelProperty(value = "체험 시작일", example = "2024-10-01")
    private Date experienceStartDate;

    @ApiModelProperty(value = "체험 마감일", example = "2024-10-15")
    private Date experienceEndDate;

    @ApiModelProperty(value = "도시", example = "부산")
    private String city;

    @ApiModelProperty(value = "구", example = "사하구")
    private String district;

    @ApiModelProperty(value = "모집 인원", example = "5")
    private Integer recruiter;

    @ApiModelProperty(value = "플랫폼", example = "블로그")
    private String platform;

    @ApiModelProperty(value = "카테고리", example = "맛집")
    private String category;

    @ApiModelProperty(value = "체험단 유형", example = "방문형")
    private String type;

    @ApiModelProperty(value = "체험단 종류", example = "프리미엄")
    private String segment;

    @ApiModelProperty(value = "삼품 URL", example = "https://example.com/campaign")
    private String campaignLink;

    @ApiModelProperty(value = "이미지 URL", example = "https://example.com/image.png")
    private String image;

    @ApiModelProperty(value = "추가할 키워드 1")
    private String keyword1;

    @ApiModelProperty(value = "추가할 키워드 2")
    private String keyword2;

    @ApiModelProperty(value = "추가할 키워드 3")
    private String keyword3;

    @ApiModelProperty(value = "미션", example = "리뷰 작성하기")
    private String mission;

    @ApiModelProperty(value = "제공 서비스/물품", example = "당일 숙박권")
    private String service;

    @ApiModelProperty(value = "포인트", example = "5000")
    private Integer point;

    @ApiModelProperty(value = "인플루언서 리스트")
    private List<InfluencerDetailResponse> influencerList;

    @ApiModelProperty(value = "등록 일자", example = "2024-09-01 12:00:00")
    private Timestamp registeredAt;

    @ApiModelProperty(value = "수정 일자", example = "2024-09-02 15:30:00")
    private Timestamp updatedAt;
}