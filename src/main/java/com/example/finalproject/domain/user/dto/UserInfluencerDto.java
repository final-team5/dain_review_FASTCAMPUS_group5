package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.entity.Influencer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfluencerDto {

    private Integer seq;

    private UserDto userDto;

    private String nickname;

    private LocalDate birthdate;

    private Integer gender;

    private String blogLink;
//    private String blogRank;
//    private Integer blogVisitors;

    private String instagramLink;
//    private String instagramRank;
//    private Integer instagramFollower;

    private String youtubeLink;
//    private String youtubeRank;
//    private Integer youtubeSubscriber;

    private String tiktokLink;
//    private String tiktokRank;
//    private Integer tiktokFollower;

    private String otherLink;
//    private String otherRank;

    public static UserInfluencerDto of(Integer seq, UserDto userDto, String nickname, LocalDate birthdate, Integer gender, String blogLink, String instagramLink, String youtubeLink, String tiktokLink, String otherLink) {
        return new UserInfluencerDto(seq, userDto, nickname, birthdate, gender, blogLink, instagramLink, youtubeLink, tiktokLink, otherLink);
    }

    public static UserInfluencerDto from(Influencer influencer) {
        return UserInfluencerDto.of(
                influencer.getSeq(),
                UserDto.from(influencer.getUser()),
                influencer.getNickname(),
                influencer.getBirthdate(),
                influencer.getGender(),
                influencer.getBlogLink(),
                influencer.getInstagramLink(),
                influencer.getYoutubeLink(),
                influencer.getTiktokLink(),
                influencer.getOtherLink()
        );
    }

}
