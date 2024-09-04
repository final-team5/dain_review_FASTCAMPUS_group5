package com.example.finalproject.controller;

import com.example.finalproject.domain.user.dto.request.BusinessesSignup;
import com.example.finalproject.domain.user.dto.request.BusinessesSocialSignup;
import com.example.finalproject.domain.user.dto.request.InfluencerSignup;
import com.example.finalproject.domain.user.dto.request.InfluencerSocialSignup;
import com.example.finalproject.domain.user.dto.request.Login;
import com.example.finalproject.domain.user.dto.LoginResponse;
import com.example.finalproject.domain.user.dto.Register;
import com.example.finalproject.domain.user.dto.SocialInfo;
import com.example.finalproject.domain.user.dto.request.SocialLogin;
import com.example.finalproject.domain.user.dto.UserInfo;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.service.UserService;
import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import com.example.finalproject.global.jwt.TokenProvider;
import com.example.finalproject.global.util.ResponseApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Api(tags = "공용")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PublicController {

	private final UserService userService;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;

	@ApiOperation(value = "로그인", tags = "공개 - 회원")
	@PostMapping("/login")
	public ResponseEntity<ResponseApi<LoginResponse>> login(@RequestBody Login login) throws ParseException {
		UserInfo userInfo = userService.getUser(login.getEmail(), 1);

		if (!passwordEncoder.matches(login.getPw(), userInfo.getPw())) {
			throw new AuthException(AuthErrorCode.PASSWORD_NOT_MATCH);
		}

		LoginResponse loginResponse = setReturnValue(userInfo);
		return ResponseEntity.ok(ResponseApi.success(HttpStatus.OK, loginResponse));
	}

	@ApiOperation(value = "소셜 로그인", tags = "공개 - 회원")
	@PostMapping("/social-login")
	public ResponseEntity<ResponseApi<LoginResponse>> SocialRegister(@RequestBody SocialLogin login) throws ParseException {

		SocialInfo info = getSocialInfo(login.getAccessToken(), login.getLoginType());
		String email = Objects.requireNonNull(info).getEmail();
		String pw = info.getId();

		UserInfo userInfo = userService.getUser(email, login.getLoginType());

		if (!passwordEncoder.matches(pw, userInfo.getPw())) {
			throw new AuthException(AuthErrorCode.PASSWORD_NOT_MATCH);
		}

		LoginResponse loginResponse = setReturnValue(userInfo);
		return ResponseEntity.ok(ResponseApi.success(HttpStatus.OK, loginResponse));

	}

	@ApiOperation(value = "회원 가입(인플루언서)", tags = "공개 - 회원")
	@PostMapping("/influencers/signup")
	public ResponseEntity<?> influencers_signup(@RequestBody InfluencerSignup signup){
		Register register = new Register(signup);
		register.setLoginType(1);
		register.setRole("ROLE_INFLUENCER");
		register.setType(2);

		userService.getU(register.getEmail(), 1);

		if(userService.checkEmail(register.getEmail())){
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}
		if(userService.checkNickname(register.getNickname())){
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}
		if(userService.checkPhone(register.getPhone())){
			throw new AuthException(AuthErrorCode.PHONE_ALREADY_IN_USE);
		}
		String pw = passwordEncoder.encode(register.getPw());
		register.setPw(pw);
		userService.signup(register);

		JSONObject jo = new JSONObject();
		jo.put("message", "회원가입 되었습니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "회원 가입(인플루언서 - 소셜)", tags = "공개 - 회원", notes = "loginTyoe = 2(구글), 3(카카오), 4(네이버)")
	@PostMapping("/influencers/social-signup")
	public ResponseEntity<?> influencers_social_signup(@RequestBody InfluencerSocialSignup signup){
		ObjectMapper objectMapper = new ObjectMapper();
		Register register = objectMapper.convertValue(signup, Register.class);
		register.setRole("ROLE_INFLUENCER");
		register.setType(2);

		SocialInfo info = getSocialInfo(signup.getAccessToken(), signup.getLoginType());
		String email = info.getEmail();
		String pw = info.getId();

		register.setEmail(email);
		register.setPw(passwordEncoder.encode(pw));

		if(userService.checkEmail(register.getEmail())){
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}
		if(userService.checkNickname(register.getNickname())){
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}
		if(userService.checkPhone(register.getPhone())){
			throw new AuthException(AuthErrorCode.PHONE_ALREADY_IN_USE);
		}
		userService.signup(register);

		JSONObject jo = new JSONObject();
		jo.put("message", "회원가입 되었습니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "회원 가입(사업자)", tags = "공개 - 회원")
	@PostMapping("/businesses/signup")
	public ResponseEntity<?> businesses_signup(@RequestBody BusinessesSignup signup){
		Register register = new Register(signup);
		register.setLoginType(1);
		register.setRole("ROLE_BUSINESSES");
		register.setType(1);

		userService.getUser(register.getEmail(), 1);

		if(userService.checkEmail(register.getEmail())){
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}
		if(userService.checkNickname(register.getNickname())){
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}
		if(userService.checkPhone(register.getPhone())){
			throw new AuthException(AuthErrorCode.PHONE_ALREADY_IN_USE);
		}
		String pw = passwordEncoder.encode(register.getPw());
		register.setPw(pw);
		userService.signup(register);

		JSONObject jo = new JSONObject();
		jo.put("message", "회원가입 되었습니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "회원 가입(사업자 - 소셜)", tags = "공개 - 회원", notes = "loginType = 2(구글), 3(카카오), 4(네이버)")
	@PostMapping("/businesses/social-signup")
	public ResponseEntity<?> businesses_social_signup(@RequestBody BusinessesSocialSignup signup){
		ObjectMapper objectMapper = new ObjectMapper();
		Register register = objectMapper.convertValue(signup, Register.class);
		register.setRole("ROLE_BUSINESSES");
		register.setType(1);

		SocialInfo info = getSocialInfo(signup.getAccessToken(), signup.getLoginType());
		String email = info.getEmail();
		String pw = info.getId();

		register.setEmail(email);
		register.setPw(passwordEncoder.encode(pw));

		if(userService.checkEmail(register.getEmail())){
			throw new AuthException(AuthErrorCode.EMAIL_ALREADY_IN_USE);
		}
		if(userService.checkNickname(register.getNickname())){
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}
		if(userService.checkPhone(register.getPhone())){
			throw new AuthException(AuthErrorCode.PHONE_ALREADY_IN_USE);
		}
		userService.signup(register);

		JSONObject jo = new JSONObject();
		jo.put("message", "회원가입 되었습니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "닉네임 중복 체크", tags = "공개 - 회원")
	@GetMapping("/nickname-check")
	public ResponseEntity<?> nicknameCheck(String nickname){

		if (userService.checkNickname(nickname)) {
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}

		JSONObject jo = new JSONObject();
		jo.put("message", "사용 가능한 닉네임 입니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	@ApiOperation(value = "회사명 중복 체크", tags = "공개 - 회원")
	@GetMapping("/company-check")
	public ResponseEntity<?> companyCheck(String company){

		if (userService.checkCompany(company)) {
			throw new AuthException(AuthErrorCode.COMPANY_ALREADY_IN_USE);
		}

		JSONObject jo = new JSONObject();
		jo.put("message", "사용 가능한 회사명 입니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
	}

	public LoginResponse setReturnValue(UserInfo user) throws ParseException {
		String new_token = tokenProvider.createToken(user);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNowDate = simpleDateFormat.format(tokenProvider.getExpireDate(new_token));
		return LoginResponse.builder()
			.token(new_token)
			.name(user.getName())
			.expireDate(strNowDate)
			.message("로그인 되었습니다.")
			.build();
	}

	private SocialInfo getSocialInfo(String accessToken, Integer loginType) {
		try {
			// HTTP 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setBearerAuth(accessToken);  // Authorization 헤더를 설정
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);  // Content-Type 설정

			// HTTP 요청 객체 생성
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response;

			// loginType에 따른 API 요청
			String url = null;
			HttpMethod method = HttpMethod.POST;  // 기본 메서드 설정

			switch (loginType) {
				case 2:
					url = "https://www.googleapis.com/oauth2/v1/userinfo";
					method = HttpMethod.GET;
					break;
				case 3:
					url = "https://kapi.kakao.com/v2/user/me";
					break;
				case 4:
					url = "https://openapi.naver.com/v1/nid/me";
					break;
				default:
					throw new IllegalArgumentException("지원하지 않는 로그인 타입: " + loginType);
			}

			response = restTemplate.exchange(url, method, request, String.class);

			// responseBody에서 JSON 파싱
			String responseBody = response.getBody();
			System.out.println(responseBody);

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(responseBody);  // JSONObject 대신 JsonNode 사용

			// email과 id 추출
			String email = jsonNode.path("email").asText();
			String id = jsonNode.path("id").asText();

			// SocialInfo 객체 생성 및 반환
			return SocialInfo.builder()
				.email(email)
				.id(id)
				.build();
		} catch (Exception e) {
			e.printStackTrace();  // 예외를 콘솔에 출력
			return null;
		}
	}

}
