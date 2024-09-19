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

    private String name;
    private String profileUrl;
    private Integer alarmCounts;

    public static UserResponse of(String name, String profileUrl, Integer alarmCounts) {
        return new UserResponse(name, profileUrl, alarmCounts);
    }

    public static UserResponse from(UserInfo userInfo) {
        return UserResponse.of(
                userInfo.getName(),
                userInfo.getProfileUrl(),
                userInfo.getAlarmCounts()
        );
    }
}
