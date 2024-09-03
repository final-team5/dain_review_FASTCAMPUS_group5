package com.example.finalproject.domain.user.dto.response;

import com.example.finalproject.domain.user.dto.UserInfluencerDto;
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
public class UserInfluencerSaveResponse {

    private Integer seq;
    private String email;
    private String nickname;
    private LocalDate birthdate;

    public static UserInfluencerSaveResponse of(Integer seq, String email, String nickname, LocalDate birthdate) {
        return new UserInfluencerSaveResponse(seq, email, nickname, birthdate);
    }

    public static UserInfluencerSaveResponse from(UserInfluencerDto userInfluencerDto) {
        return UserInfluencerSaveResponse.of(
                userInfluencerDto.getSeq(),
                userInfluencerDto.getUserDto().getEmail(),
                userInfluencerDto.getNickname(),
                userInfluencerDto.getBirthdate()
        );
    }
}
