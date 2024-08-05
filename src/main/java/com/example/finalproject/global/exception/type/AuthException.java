package com.example.finalproject.global.exception.type;

import com.example.finalproject.global.exception.error.ErrorCode;

public class AuthException extends CustomException{
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
