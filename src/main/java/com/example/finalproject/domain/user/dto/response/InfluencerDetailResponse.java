package com.example.finalproject.domain.user.dto.response;

import com.example.finalproject.domain.user.entity.Influencer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "인플루언서 리스트")
public class InfluencerDetailResponse {

    @ApiModelProperty(value = "인플루언서 ID", example = "1")
    private Long influencerId;

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

    public InfluencerDetailResponse(Long influencerId, String name, Integer application, String designatedPlatform,
                                    Influencer influencer) {
        this.influencerId = influencerId;
        this.name = name;
        this.application = application;
        this.platformRank = calculatePlatformRank(designatedPlatform, influencer);
        this.platformUrl = calculatePlatformUrl(designatedPlatform, influencer);
        this.followerCountOrVisitorCount = getFollowerCount(designatedPlatform, influencer);
        this.cancelCount = influencer.getUser().getCancel();
        this.contact = influencer.getUser().getPhone();
        this.reviewRegistrationDate = "2024-09-01"; // 리뷰 등록일 예
        this.attachmentFile = null; // 첨부파일
    }

    private String calculatePlatformRank(String platform, Influencer influencer) {
        switch (platform) {
            case "Instagram":
                return influencer.getInstagramFollower() != null ? "Gold" : null;
            case "YouTube":
                return influencer.getYoutubeSubscriber() != null ? "Silver" : null;
            case "TikTok":
                return influencer.getTiktokFollower() != null ? "Bronze" : null;
            case "Blog":
                return influencer.getBlogVisitors() != null ? "Standard" : null;
            default:
                return null;
        }
    }

    private String calculatePlatformUrl(String platform, Influencer influencer) {
        switch (platform) {
            case "Instagram":
                return influencer.getInstagramLink();
            case "YouTube":
                return influencer.getYoutubeLink();
            case "TikTok":
                return influencer.getTiktokLink();
            case "Blog":
                return influencer.getBlogLink();
            default:
                return null;
        }
    }

    private Integer getFollowerCount(String platform, Influencer influencer) {
        switch (platform) {
            case "Instagram":
                return influencer.getInstagramFollower();
            case "YouTube":
                return influencer.getYoutubeSubscriber();
            case "TikTok":
                return influencer.getTiktokFollower();
            case "Blog":
                return influencer.getBlogVisitors();
            default:
                return null;
        }
    }
}