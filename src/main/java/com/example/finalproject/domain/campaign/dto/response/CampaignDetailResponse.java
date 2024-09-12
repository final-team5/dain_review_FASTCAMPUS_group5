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

import java.sql.Timestamp;
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

    @ApiModelProperty(value = "상호명", example = "ABC 리조트")
    private String companyName;

    @ApiModelProperty(value = "주소", example = "부산광역시 해운대구 우동 산 140")
    private String address;

    @ApiModelProperty(value = "연락처", example = "051-123-4567")
    private String contact;

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

    @ApiModelProperty(value = "플랫폼", example = "인스타그램")
    private String platform;

    @ApiModelProperty(value = "카테고리", example = "3")
    private String category;

    @ApiModelProperty(value = "체험단 유형", example = "방문형")
    private String type;

    @ApiModelProperty(value = "체험단 종류", example = "프리미엄")
    private String segment;

    @ApiModelProperty(value = "삼품 URL", example = "https://example.com/campaign")
    private String campaignLink;

    @ApiModelProperty(value = "이미지 URL", example = "https://example.com/image-1.png")
    private String image;

    @ApiModelProperty(value = "추가할 키워드 1", example = "부산휴가")
    private String keyword1;

    @ApiModelProperty(value = "추가할 키워드 2", example = "부산호텔")
    private String keyword2;

    @ApiModelProperty(value = "추가할 키워드 3", example = "부산숙소")
    private String keyword3;

    @ApiModelProperty(value = "미션", example = "네이버 예약 후 방문 필수, 네이버 예약 후 후기 필수로 작성부탁드립니다.")
    private String mission;

    @ApiModelProperty(value = "제공 서비스/물품", example = "당일 숙박권")
    private String service;

    @ApiModelProperty(value = "포인트", example = "100000")
    private Integer point;

    @ApiModelProperty(value = "인플루언서 리스트")
    private List<InfluencerDetailResponse> influencerList;

    @ApiModelProperty(value = "선정자 발표일", example = "2024-09-30")
    private Date applicationParticipantsDate;

    @ApiModelProperty(value = "리뷰 마감일", example = "2024-10-20")
    private Date reviewEndDate;

    @ApiModelProperty(value = "위도", example = "35.1379222")
    private Double latitude;

    @ApiModelProperty(value = "경도", example = "129.05562775")
    private Double longitude;

    @ApiModelProperty(value = "등록 일자", example = "2024-09-01 12:00:00")
    private Timestamp registeredAt;

    @ApiModelProperty(value = "수정 일자", example = "2024-09-02 15:30:00")
    private Timestamp updatedAt;
}