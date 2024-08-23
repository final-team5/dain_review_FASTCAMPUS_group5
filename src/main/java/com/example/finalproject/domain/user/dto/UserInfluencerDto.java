package com.example.finalproject.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfluencerDto {

    private Integer seq;

    private UserDto userDto;

    private String nickname;

    private Date birthdate;

    private String Gender;

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

}
