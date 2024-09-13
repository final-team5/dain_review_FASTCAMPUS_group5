package com.example.finalproject.global.exception;

import com.example.finalproject.global.exception.type.AuthException;
import com.example.finalproject.global.exception.type.ValidException;
import com.example.finalproject.global.util.ResponseApi;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> notValidException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();

		Map<String, String> errorMessages = new HashMap<>();

		for (FieldError error : result.getFieldErrors()) {
			errorMessages.put(error.getField(), error.getDefaultMessage());
		}
		ValidException validException = new ValidException(HttpStatus.BAD_REQUEST, errorMessages);

		log.warn(validException.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			ResponseApi.failed(validException, errorMessages));
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<?> notValidFormatException(InvalidFormatException ex) {

		Map<String, String> errorMessages = new HashMap<>();
		errorMessages.put("InvalidFormatException", ex.getMessage());
		ValidException validException = new ValidException(HttpStatus.BAD_REQUEST, errorMessages);

		log.warn(validException.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
				ResponseApi.failed(validException, errorMessages));
	}

	@ExceptionHandler(AuthException.class)
	public ResponseEntity<ResponseApi<?>> authException(AuthException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode()).body(ResponseApi.failed(ex));
	}

	@ExceptionHandler(ValidException.class)
	public ResponseEntity<ResponseApi<?>> authException(ValidException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode()).body(ResponseApi.failed(ex, ex.getInfo()));
	}

	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ResponseApi<?>> numberFormatException(NumberFormatException ex) {
		log.warn(ex.getMessage());

		Map<String, String> errorMessages = new HashMap<>();
		errorMessages.put("NumberFormatException", "올바르지 않은 형식입니다.");
		ValidException validException = new ValidException(HttpStatus.BAD_REQUEST, errorMessages);

		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ResponseApi.failed(validException, errorMessages));
	}

}
