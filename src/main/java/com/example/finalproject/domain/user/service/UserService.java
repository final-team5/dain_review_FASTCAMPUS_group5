package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.user.dto.*;
import com.example.finalproject.domain.user.dto.request.BusinessesSignup;
import com.example.finalproject.domain.user.dto.request.InfluencerSignup;
import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.entity.enums.SignUpSourceType;
import com.example.finalproject.domain.user.repository.BusinessesRepository;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import com.example.finalproject.global.exception.type.ValidException;
import com.example.finalproject.global.jwt.TokenProvider;
import com.example.finalproject.global.util.FileUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final InfluencerRepository influencerRepository;
	private final BusinessesRepository businessesRepository;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;

	public UserInfo getUser(String email, Integer loginType) {
		User user = userRepository.getByEmailAndType(email, loginType);

		return UserInfo.builder()
			.seq(user.getSeq())
			.email(user.getEmail())
			.id(user.getId())
			.pw(user.getPw())
			.role(user.getRole())
			.name(user.getName())
			.phone(user.getPhone())
			.createDate(user.getCreateDate())
			.signupSource(user.getSignupSource())
			.postalCode(user.getPostalCode())
			.address(user.getAddress())
			.addressDetail(user.getAddressDetail())
			.point(user.getPoint())
			.status(user.getStatus())
			.cancel(user.getCancel())
			.penalty(user.getPenalty())
			.type(user.getType())
			.build()
			;
	}

	public UserInfo findByUsername(String username) {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		return UserInfo.builder()
				.seq(user.getSeq())
				.email(user.getEmail())
				.id(user.getId())
				.pw(user.getPw())
				.role(user.getRole())
				.name(user.getName())
				.phone(user.getPhone())
				.createDate(user.getCreateDate())
				.signupSource(user.getSignupSource())
				.postalCode(user.getPostalCode())
				.address(user.getAddress())
				.addressDetail(user.getAddressDetail())
				.point(user.getPoint())
				.status(user.getStatus())
				.cancel(user.getCancel())
				.penalty(user.getPenalty())
				.type(user.getType())
				.build();
	}

	public boolean checkNickname(String nickname) {
		return influencerRepository.existsByNickname(nickname);
	}

	public boolean checkCompany(String company) {
		return businessesRepository.existsByCompany(company);
	}

	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean checkPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}


	/**
	 * 인플루언서 회원가입 기능
	 *
	 * @param influencerSignup : 인플루언서 회원가입 정보
	 * @param profile : 회원 프로필 사진
	 * @return UserInfluencerDto
	 */
	@Transactional
	public UserInfluencerDto registerInfluencer(InfluencerSignup influencerSignup, MultipartFile profile) {
		if (checkEmail(influencerSignup.getEmail())) {
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}

		if (checkNickname(influencerSignup.getNickname())) {
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}

		validateSignUpSource(influencerSignup.getSignupSource());

		validateSNSAtLeastOneSelected(influencerSignup);

		String encodedPassword = passwordEncoder.encode(influencerSignup.getPw());

		// TODO : 프로필 S3 저장 후 url 가져오기

		User user = User.of(
				influencerSignup.getEmail(),
				influencerSignup.getEmail(),
				encodedPassword,
				"ROLE_INFLUENCER",
				influencerSignup.getName(),
				influencerSignup.getPhone(),
				"image example",
				1,
				null,
				influencerSignup.getSignupSource().getInfo(),
				Integer.valueOf(influencerSignup.getPostalCode()),
				influencerSignup.getAddress(),
				influencerSignup.getAddressDetail(),
				0,
				0,
				0,
				0,
				1
		);
		User savedUser = userRepository.save(user);

		Influencer influencer = Influencer.of(
				savedUser,
				influencerSignup.getNickname(),
				influencerSignup.getGender(),
				LocalDate.parse(influencerSignup.getBirthdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				influencerSignup.getBlog(),
				influencerSignup.getInstagram(),
				influencerSignup.getYoutube(),
				influencerSignup.getTiktok(),
				influencerSignup.getOther()
		);
		Influencer savedInfluencer = influencerRepository.save(influencer);

		return UserInfluencerDto.from(savedInfluencer);
	}

	/**
	 * 사업주 회원가입 기능
	 *
	 * @param businessesSignup : 사업주 회원가입 정보
	 * @param profile : 회원 프로필 사진
	 * @return UserBusinessesDto
	 */
	@Transactional
	public UserBusinessesDto registerBusinesses(BusinessesSignup businessesSignup, MultipartFile profile) {
		if (checkEmail(businessesSignup.getEmail())) {
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}

		if (checkNickname(businessesSignup.getCompany())) {
			throw new AuthException(AuthErrorCode.COMPANY_ALREADY_IN_USE);
		}

		validateSignUpSource(businessesSignup.getSignupSource());

		String encodedPassword = passwordEncoder.encode(businessesSignup.getPw());

		// TODO : 프로필 S3 저장 후 url 가져오기

		User user = User.of(
				businessesSignup.getEmail(),
				businessesSignup.getEmail(),
				encodedPassword,
				"ROLE_BUSINESSES",
				businessesSignup.getName(),
				businessesSignup.getPhone(),
				"image example",
				1,
				null,
				businessesSignup.getSignupSource().getInfo(),
				Integer.valueOf(businessesSignup.getPostalCode()),
				businessesSignup.getAddress(),
				businessesSignup.getAddressDetail(),
				0,
				0,
				0,
				0,
				2
		);
		User savedUser = userRepository.save(user);

		Businesses businesses = Businesses.of(
				savedUser,
				businessesSignup.getCompany(),
				businessesSignup.getPhone(),
				businessesSignup.getName()
		);

		Businesses savedBusinesses = businessesRepository.save(businesses);

		return UserBusinessesDto.from(savedBusinesses);
	}

	private static void validateSNSAtLeastOneSelected(InfluencerSignup influencerSignup) {
		if (influencerSignup.getBlog() == null && influencerSignup.getInstagram() == null && influencerSignup.getYoutube() == null && influencerSignup.getTiktok() == null && influencerSignup.getOther() == null) {
			throw new ValidException(ValidErrorCode.SNS_DOES_NOT_SELECTED);
		}
	}

	private static void validateSignUpSource(SignUpSourceType signUpSourceType) {
		if (!signUpSourceType.equals(SignUpSourceType.PORTAL_SEARCH) && !signUpSourceType.equals(SignUpSourceType.SNS) && !signUpSourceType.equals(SignUpSourceType.INTRODUCTION_TO_ACQUAINTANCES) && !signUpSourceType.equals(SignUpSourceType.ETC)) {
			throw new ValidException(ValidErrorCode.SIGN_UP_SOURCE_NOT_VALID);
		}
	}

}
