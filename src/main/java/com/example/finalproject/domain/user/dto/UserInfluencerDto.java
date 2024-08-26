package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.entity.Influencer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserInfluencerDto {

    private Integer seq;

    private UserDto userDto;

    private String nickname;

    private Date birthdate;

    private Integer gender;

    private String blogLink;
    private String blogRank;
    private Integer blogVisitors;

    private String instagramLink;
    private String instagramRank;
    private Integer instagramFollower;

    private String youtubeLink;
    private String youtubeRank;
    private Integer youtubeSubscriber;

    private String tiktokLink;
    private String tiktokRank;
    private Integer tiktokFollower;

    private String otherLink;
    private String otherRank;

    public static UserInfluencerDto of(Integer seq, UserDto userDto, String nickname, Date birthdate, Integer gender, String blogLink, String blogRank, Integer blogVisitors, String instagramLink, String instagramRank, Integer instagramFollower, String youtubeLink, String youtubeRank, Integer youtubeSubscriber, String tiktokLink, String tiktokRank, Integer tiktokFollower, String otherLink, String otherRank) {
        return new UserInfluencerDto(seq,
                userDto,
                nickname,
                birthdate,
                gender,
                blogLink,
                blogRank,
                blogVisitors,
                instagramLink,
                instagramRank,
                instagramFollower,
                youtubeLink,
                youtubeRank,
                youtubeSubscriber,
                tiktokLink,
                tiktokRank,
                tiktokFollower,
                otherLink,
                otherRank);
    }

    public static UserInfluencerDto from(Influencer influencer) {
        return UserInfluencerDto.of(
                influencer.getSeq(),
                UserDto.from(influencer.getUser()),
                influencer.getNickname(),
                influencer.getBirthdate(),
                influencer.getGender(),
                influencer.getBlogLink(),
                influencer.getBlogRank(),
                influencer.getBlogVisitors(),
                influencer.getInstagramLink(),
                influencer.getInstagramRank(),
                influencer.getInstagramFollower(),
                influencer.getYoutubeLink(),
                influencer.getYoutubeRank(),
                influencer.getYoutubeSubscriber(),
                influencer.getTiktokLink(),
                influencer.getTiktokRank(),
                influencer.getTiktokFollower(),
                influencer.getOtherLink(),
                influencer.getOtherRank()
        );
    }

}
