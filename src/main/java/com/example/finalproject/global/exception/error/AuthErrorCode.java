package com.example.finalproject.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode{

	INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
	EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	UNSUPPORTED_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰 인증 방식입니다."),
	UNKNOWN_AUTH_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 토큰 오류입니다."),
	ALREADY_LOGOUT(HttpStatus.BAD_REQUEST, "이미 로그아웃 된 유저입니다."),
	NOT_FOUND_USER(HttpStatus.BAD_REQUEST,"사용자를 찾을 수 없습니다"),
	PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다"),
	NICKNAME_ALREADY_IN_USE(HttpStatus.CONFLICT,"이미 사용중인 닉네임 입니다"),
	COMPANY_ALREADY_IN_USE(HttpStatus.CONFLICT,"이미 사용중인 회사명 입니다")
	;

	private final HttpStatus code;
	private final String info;

}
