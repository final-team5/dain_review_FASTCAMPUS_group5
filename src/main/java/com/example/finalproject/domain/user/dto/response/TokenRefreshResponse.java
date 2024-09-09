package com.example.finalproject.domain.user.dto.response;

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
public class TokenRefreshResponse {

    private String refreshedToken;
    private String email;
    private String expiredDate;

    public static TokenRefreshResponse of(String refreshedToken, String email, String expiredDate) {
        return new TokenRefreshResponse(refreshedToken, email, expiredDate);
    }
}
