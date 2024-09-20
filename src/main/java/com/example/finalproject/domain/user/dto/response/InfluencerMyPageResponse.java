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
public class InfluencerMyPageResponse {

    private Integer seq;
    private String role;
    private String nickname;
    private String profileUrl;
    private String instagram;
    private String blog;
    private String youtube;
    private String tiktok;
    private Integer point;

    private Integer applicationCounts;
    private Integer selectedCounts;
    private Integer progressCounts;
}
