package com.example.finalproject.global.exception;

import com.example.finalproject.global.util.Response;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {

	// exception 응답 구조 생성
	public static Response<Object> createErrorResponse(List<String> errorMessage, HttpStatus status) {
		Response.Error error = Response.Error
			.builder()
			.errorMessage(errorMessage)
			.build();

		return Response.builder()
			.resultCode(String.valueOf(status.value()))
			.resultMessage(status.toString())
			.error(error)
			.build();
	}
}