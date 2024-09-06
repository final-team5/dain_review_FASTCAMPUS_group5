package com.example.finalproject.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerSignup {
	private String email;
	private String pw;
	private String name;
	private String phone;
	private String nickname;
	private String signupSource;

	private String blog;
	private String instagram;
	private String youtube;
	private String tiktok;
	private String other;

	private String postalCode;
	private String address;
	private String addressDetail;

	private String birthdate;
	private Integer gender;

	private String profile;

}