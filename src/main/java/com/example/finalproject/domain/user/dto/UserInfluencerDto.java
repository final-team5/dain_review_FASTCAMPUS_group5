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

    private String nickName;

    private Date birthDate;

    private String Gender;

    private String blogLink;
    private String blogRank;
    private Integer blogVisitors;

    private String instagramLink;
    private String instagramRank;
    private Integer instagramVisitors;

    private String youtubeLink;
    private String youtubeRank;
    private Integer youtubeVisitors;

    private String tiktokLink;
    private String tiktokRank;
    private Integer tiktokVisitors;

    private String otherLink;
    private String otherRank;


}
