package com.example.finalproject.domain.user.dto.request;

import com.example.finalproject.domain.user.entity.enums.SignUpSourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerSignup {
	private String email;
	private String pw;
	private String name;
	private String phone;
	private String nickname;
//	private String signupSource;
	private SignUpSourceType signupSource;

	private String blog;
	private String instagram;
	private String youtube;
	private String tiktok;
	private String other;

	private String postalCode;
	private String address;
	private String addressDetail;

//	private MultipartFile profile;
	private String birthdate;
	private Integer gender;

}