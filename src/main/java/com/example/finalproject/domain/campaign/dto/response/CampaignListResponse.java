package com.example.finalproject.domain.campaign.dto.response;

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
public class CampaignListResponse {

    private Integer seq;
    private String title;
    private String segment;
    private Integer point;
    private Integer recruiter;
    private String email;
    private String companyName;
    private String platform;
    private String type;
    private String phone;
    private String registeredAt;
    private String updatedAt;
    private String status;

}
