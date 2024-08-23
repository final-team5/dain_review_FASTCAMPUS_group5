package com.example.finalproject.global.exception.type;

import java.util.Map;

import com.example.finalproject.global.exception.error.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValidException extends CustomException {
    public ValidException(HttpStatus statusCode, Map<String, String> info){
        super(statusCode, info);
    }

    public ValidException(ErrorCode errorCode) {
        super(errorCode);
    }
}
