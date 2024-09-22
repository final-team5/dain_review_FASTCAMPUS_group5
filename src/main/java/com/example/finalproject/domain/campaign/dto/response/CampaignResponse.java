package com.example.finalproject.domain.campaign.dto.response;

import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignResponse {

    private Integer seq;
    private Integer userSeq;
    private String id;
    private String category;
    private String platform;
    private String type;
    private String segment;
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
    private Date experienceStartTime;
    private Date experienceEndTime;
    private Date applicationParticipantsDate;
    private Date reviewEndDate;
    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    private Integer sunday;
    private Integer hashtag;
    private Integer map;
    private Integer link;
    private Integer characters;
    private Integer videoLength;
    private Integer pictureCount;
    private Integer accountTag;
    private Integer sound;
    private Integer advertisingDisplay;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Double latitude;
    private Double longitude;

    public static CampaignResponse of(
            Integer seq,
            Integer userSeq,
            String id,
            String category,
            String platform,
            String type,
            String segment,
            String title,
            String contents,
            String service,
            String keyword1,
            String keyword2,
            String keyword3,
            String campaignLink,
            String mission,
            Integer recruiter,
            String image,
            String city,
            String district,
            Integer point,
            String tag,
            Integer status,
            Date applicationStartDate,
            Date applicationEndDate,
            Date experienceStartDate,
            Date experienceEndDate,
            Date experienceStartTime,
            Date experienceEndTime,
            Date applicationParticipantsDate,
            Date reviewEndDate,
            Integer monday,
            Integer tuesday,
            Integer wednesday,
            Integer thursday,
            Integer friday,
            Integer saturday,
            Integer sunday,
            Integer hashtag,
            Integer map,
            Integer link,
            Integer characters,
            Integer videoLength,
            Integer pictureCount,
            Integer accountTag,
            Integer sound,
            Integer advertisingDisplay,
            Timestamp registeredAt,
            Timestamp updatedAt,
            Double latitude,
            Double longitude
    ) {
        return new CampaignResponse(seq, userSeq, id, category, platform, type, segment, title, contents, service, keyword1, keyword2, keyword3,
                campaignLink, mission, recruiter, image, city, district, point, tag, status, applicationStartDate, applicationEndDate,
                experienceStartDate, experienceEndDate, experienceStartTime, experienceEndTime, applicationParticipantsDate, reviewEndDate,
                monday, tuesday, wednesday, thursday, friday, saturday, sunday, hashtag, map, link, characters, videoLength, pictureCount, accountTag, sound,
                advertisingDisplay, registeredAt, updatedAt, latitude, longitude);
    }

    public static CampaignResponse from(CampaignDto campaignDto) {
        return CampaignResponse.of(
                campaignDto.getSeq(),
                campaignDto.getUserDto().getSeq(),
                campaignDto.getId(),
                campaignDto.getCategory(),
                campaignDto.getPlatform(),
                campaignDto.getType(),
                campaignDto.getSegment(),
                campaignDto.getTitle(),
                campaignDto.getContents(),
                campaignDto.getService(),
                campaignDto.getKeyword1(),
                campaignDto.getKeyword2(),
                campaignDto.getKeyword3(),
                campaignDto.getCampaignLink(),
                campaignDto.getMission(),
                campaignDto.getRecruiter(),
                campaignDto.getImage(),
                campaignDto.getCity(),
                campaignDto.getDistrict(),
                campaignDto.getPoint(),
                campaignDto.getTag(),
                campaignDto.getStatus(),
                campaignDto.getApplicationStartDate(),
                campaignDto.getApplicationEndDate(),
                campaignDto.getExperienceStartDate(),
                campaignDto.getExperienceEndDate(),
                campaignDto.getExperienceStartTime(),
                campaignDto.getExperienceEndTime(),
                campaignDto.getApplicationParticipantsDate(),
                campaignDto.getReviewEndDate(),
                campaignDto.getMonday(),
                campaignDto.getTuesday(),
                campaignDto.getWednesday(),
                campaignDto.getThursday(),
                campaignDto.getFriday(),
                campaignDto.getSaturday(),
                campaignDto.getSunday(),
                campaignDto.getHashtag(),
                campaignDto.getMap(),
                campaignDto.getLink(),
                campaignDto.getCharacters(),
                campaignDto.getVideoLength(),
                campaignDto.getPictureCount(),
                campaignDto.getAccountTag(),
                campaignDto.getSound(),
                campaignDto.getAdvertisingDisplay(),
                campaignDto.getRegisteredAt(),
                campaignDto.getUpdatedAt(),
                campaignDto.getLatitude(),
                campaignDto.getLongitude()
        );
    }
}
