package com.example.finalproject.domain.user.dto.response;

import com.example.finalproject.domain.user.dto.UserInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Integer seq;
    private String email;
    private String name;
    private String profileUrl;
    private Integer alarmCounts;
    private String role;

    public static UserResponse of(Integer seq, String email, String name, String profileUrl, Integer alarmCounts, String role) {
        return new UserResponse(seq, email, name, profileUrl, alarmCounts, role);
    }

    public static UserResponse from(UserInfo userInfo) {
        return UserResponse.of(
                userInfo.getSeq(),
                userInfo.getEmail(),
                userInfo.getName(),
                userInfo.getProfileUrl(),
                userInfo.getAlarmCounts(),
                userInfo.getRole()
        );
    }
}
