package com.example.finalproject.domain.user.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "인플루언서 리스트")
public class InfluencerDetailResponse {

    @ApiModelProperty(value = "인플루언서 ID", example = "1")
    private String influencerId;

    @ApiModelProperty(value = "인플루언서 이름", example = "홍길동")
    private String name;

    @ApiModelProperty(value = "선정여부", example = "1")
    private Integer application;

    @ApiModelProperty(value = "플랫폼 등급", example = "고급")
    private String platformRank;

    @ApiModelProperty(value = "플랫폼 URL", example = "https://example.com/profile")
    private String platformUrl;

    @ApiModelProperty(value = "방문수/팔로워", example = "1500")
    private Integer followerCountOrVisitorCount;

    @ApiModelProperty(value = "취소 횟수", example = "1")
    private Integer cancelCount;

    @ApiModelProperty(value = "연락처", example = "010-1111-2222")
    private String contact;

    @ApiModelProperty(value = "리뷰 등록일", example = "2024-09-01")
    private String reviewRegistrationDate;

    @ApiModelProperty(value = "첨부파일", example = "attachment.png")
    private String attachmentFile;

}