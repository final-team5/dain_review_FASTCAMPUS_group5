package com.example.finalproject.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidErrorCode implements ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.")
    ;

    private final HttpStatus code;
    private final String info;
}
