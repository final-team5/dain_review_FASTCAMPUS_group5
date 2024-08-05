package com.example.finalproject.global.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	HttpStatus getCode();
	String getInfo();
}
