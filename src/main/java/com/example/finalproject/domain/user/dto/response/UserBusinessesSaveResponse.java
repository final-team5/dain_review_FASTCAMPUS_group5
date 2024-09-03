package com.example.finalproject.domain.user.dto.response;

import com.example.finalproject.domain.user.dto.UserBusinessesDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBusinessesSaveResponse {

    private Integer seq;
    private String email;
    private String company;

    public static UserBusinessesSaveResponse of(Integer seq, String email, String company) {
        return new UserBusinessesSaveResponse(seq, email, company);
    }

    public static UserBusinessesSaveResponse from(UserBusinessesDto userBusinessesDto) {
        return UserBusinessesSaveResponse.of(
                userBusinessesDto.getSeq(),
                userBusinessesDto.getUserDto().getEmail(),
                userBusinessesDto.getCompany()
        );
    }
}
