package com.example.finalproject.domain.campaign.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.models.auth.In;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignSearchResponse {
	private Integer seq;
	private String image;
	private String title;
	private String service;
	private Integer recruiter;
	private Date applicationEndDate;
	private String segment;
	private String platform;
	private String type;
	private String city;
	private String district;
}
