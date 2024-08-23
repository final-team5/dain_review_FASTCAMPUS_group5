package com.example.finalproject.controller;

import com.example.finalproject.domain.user.dto.Login;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.entity.UserInfo;
import com.example.finalproject.domain.user.service.UserService;
import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import com.example.finalproject.global.jwt.TokenProvider;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<ResponseApi<UserInfo>> login(@RequestBody Login login) throws ParseException {

		UserInfo userInfo = userService.getUser(login.getEmail(), 1);

		if (!passwordEncoder.matches(login.getPw(), userInfo.getPw())) {
			throw new AuthException(AuthErrorCode.PASSWORD_NOT_MATCH);
		}

		ResponseApi<UserInfo> response = ResponseApi.success(HttpStatus.OK, userInfo);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}



	@ApiOperation(value = "닉네임 중복 체크", tags = "공개 - 회원")
	@GetMapping("/nickname-check")
	public ResponseEntity<?> nicknameCheck(String nickname){
		JSONObject jo = new JSONObject();

		if (userService.checkNickname(nickname)) {
			throw new AuthException(AuthErrorCode.NICKNAME_ALREADY_IN_USE);
		}
		jo.put("message", "사용 가능한 닉네임 입니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

	}

	@ApiOperation(value = "회사명 중복 체크", tags = "공개 - 회원")
	@GetMapping("/company-check")
	public ResponseEntity<?> compnayCheck(String company){
		JSONObject jo = new JSONObject();

		if (userService.checkCompany(company)) {
			throw new AuthException(AuthErrorCode.COMPANY_ALREADY_IN_USE);
		}
		jo.put("message", "사용 가능한 회사명 입니다");
		return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

	}

}
