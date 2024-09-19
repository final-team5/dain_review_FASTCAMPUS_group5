package com.example.finalproject.domain.campaign.dto;

import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignDto {

    private Integer seq;
    private UserDto userDto;
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

    public static CampaignDto of(Integer seq, UserDto userDto, String id, String category, String platform, String type, String segment,
                                 String title, String contents, String service, String keyword1, String keyword2, String keyword3,
                                 String campaignLink, String mission, Integer recruiter, String image, String city, String district,
                                 Integer point, String tag, Integer status, Date applicationStartDate, Date applicationEndDate,
                                 Date experienceStartDate, Date experienceEndDate, Date experienceStartTime, Date experienceEndTime,
                                 Date applicationParticipantsDate, Date reviewEndDate, Integer monday, Integer tuesday, Integer wednesday,
                                 Integer thursday, Integer friday, Integer saturday, Integer sunday, Integer hashtag, Integer map, Integer link,
                                 Integer characters, Integer videoLength, Integer pictureCount, Integer accountTag, Integer sound, Integer advertisingDisplay,
                                 Timestamp registeredAt, Timestamp updatedAt, Double latitude, Double longitude) {
        return new CampaignDto(seq, userDto, id, category, platform, type, segment, title, contents, service,
                keyword1, keyword2, keyword3, campaignLink, mission, recruiter, image, city, district,
                point, tag, status, applicationStartDate, applicationEndDate, experienceStartDate, experienceEndDate,
                experienceStartTime, experienceEndTime, applicationParticipantsDate, reviewEndDate, monday, tuesday, wednesday,
                thursday, friday, saturday, sunday, hashtag, map, link, characters, videoLength, pictureCount, accountTag, sound,
                advertisingDisplay, registeredAt, updatedAt, latitude, longitude);
    }

    public static CampaignDto from(Campaign campaign) {
        if (campaign == null) {
            return new CampaignDto();
        }

        return CampaignDto.of(
                campaign.getSeq(),
                UserDto.from(campaign.getUser()),
                campaign.getId(),
                campaign.getCategory(),
                campaign.getPlatform(),
                campaign.getType(),
                campaign.getSegment(),
                campaign.getTitle(),
                campaign.getContents(),
                campaign.getService(),
                campaign.getKeyword1(),
                campaign.getKeyword2(),
                campaign.getKeyword3(),
                campaign.getCampaignLink(),
                campaign.getMission(),
                campaign.getRecruiter(),
                campaign.getImage(),
                campaign.getCity(),
                campaign.getDistrict(),
                campaign.getPoint(),
                campaign.getTag(),
                campaign.getStatus(),
                campaign.getApplicationStartDate(),
                campaign.getApplicationEndDate(),
                campaign.getExperienceStartDate(),
                campaign.getExperienceEndDate(),
                campaign.getExperienceStartTime(),
                campaign.getExperienceEndTime(),
                campaign.getApplicationParticipantsDate(),
                campaign.getReviewEndDate(),
                campaign.getMonday(),
                campaign.getTuesday(),
                campaign.getWednesday(),
                campaign.getThursday(),
                campaign.getFriday(),
                campaign.getSaturday(),
                campaign.getSunday(),
                campaign.getHashtag(),
                campaign.getMap(),
                campaign.getLink(),
                campaign.getCharacters(),
                campaign.getVideoLength(),
                campaign.getPictureCount(),
                campaign.getAccountTag(),
                campaign.getSound(),
                campaign.getAdvertisingDisplay(),
                campaign.getRegisteredAt(),
                campaign.getUpdatedAt(),
                campaign.getLatitude(),
                campaign.getLongitude()
        );
    }
}