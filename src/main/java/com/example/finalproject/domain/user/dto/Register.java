package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.dto.request.BusinessesSignup;
import com.example.finalproject.domain.user.dto.request.InfluencerSignup;
import com.example.finalproject.domain.user.dto.request.InfluencerSocialSignup;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Register {
	//공용
	private String email;
	private String name;
	private String phone;
	private String signupSource;
	private Integer loginType;
	private String role;
	private Integer type;
	private String postalCode;
	private String address;
	private String addressDetail;

	private byte[] profile;
	private String fileName;

	//소셜 가입
	private String accessToken;

	//로컬 가입
	private Integer authNum;
	private String id;
	private String pw;

	//인플루언서 항목
	private String nickname;
	private String birthdate;
	private Integer gender;

	private String blog;
	private String instagram;
	private String youtube;
	private String tiktok;
	private String other;

	//사업자 항목
	private String company;

	public Register(InfluencerSignup signup) {
		this.email = signup.getEmail();
		this.pw = signup.getPw();
		this.name = signup.getName();
		this.phone = signup.getPhone();
		this.nickname = signup.getNickname();
		this.signupSource = signup.getSignupSource();
		this.blog = signup.getBlog();
		this.instagram = signup.getInstagram();
		this.youtube = signup.getYoutube();
		this.tiktok = signup.getTiktok();
		this.other = signup.getOther();
		this.postalCode = signup.getPostalCode();
		this.address = signup.getAddress();
		this.addressDetail = signup.getAddressDetail();
		this.birthdate = signup.getBirthdate();
		this.gender = signup.getGender();
		/*try {
			this.profile = signup.getProfile().getBytes();
			this.fileName = signup.getProfile().getOriginalFilename();
		} catch (IOException e){
			System.out.println(e);
		}*/
	}

	public Register(BusinessesSignup signup){
		this.email = signup.getEmail();
		this.pw = signup.getPw();
		this.name = signup.getName();
		this.phone = signup.getPhone();
		this.company = signup.getCompany();
		this.signupSource = signup.getSignupSource();
		this.postalCode = signup.getPostalCode();
		this.address = signup.getAddress();
		this.addressDetail = signup.getAddressDetail();
		try {
			this.profile = signup.getProfile().getBytes();
			this.fileName = signup.getProfile().getOriginalFilename();
		} catch (IOException e){
			System.out.println(e);
		}
	}

	public Register(InfluencerSocialSignup signup){
		this.loginType = signup.getLoginType();
		this.name = signup.getName();
		this.phone = signup.getPhone();
		this.nickname = signup.getNickname();
		this.signupSource = signup.getSignupSource();
		this.blog = signup.getBlog();
		this.instagram = signup.getInstagram();
		this.youtube = signup.getYoutube();
		this.tiktok = signup.getTiktok();
		this.other = signup.getOther();
		this.postalCode = signup.getPostalCode();
		this.address = signup.getAddress();
		this.addressDetail = signup.getAddressDetail();
		this.birthdate = signup.getBirthdate();
		this.gender = signup.getGender();

		try {
			this.profile = signup.getProfile().getBytes();
			this.fileName = signup.getProfile().getOriginalFilename();
		} catch (IOException e){
			System.out.println(e);
		}
	}
//
//	public Register(BusinessesSocialSignup signup){
//		this.loginType = signup.getLoginType();
//		this.name = signup.getName();
//		this.phone = signup.getPhone();
//		this.company = signup.getCompany();
//		this.signupSource = signup.getSignupSource();
//		this.postalCode = signup.getPostalCode();
//		this.address = signup.getAddress();
//		this.addressDetail = signup.getAddressDetail();
//		try {
//			this.profile = signup.getProfile().getBytes();
//			this.fileName = signup.getProfile().getOriginalFilename();
//		} catch (IOException e){
//			System.out.println(e);
//		}
//	}
}