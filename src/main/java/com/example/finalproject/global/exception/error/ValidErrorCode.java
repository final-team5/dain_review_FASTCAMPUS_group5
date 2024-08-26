package com.example.finalproject.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidErrorCode implements ErrorCode {

    // Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    POST_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 SNS 종류가 존재하지 않습니다."),
    POST_USER_MISMATCH(HttpStatus.BAD_REQUEST, "해당 게시글의 작성자가 아닙니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),

    // PostComment
    POST_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "작성된 댓글 정보가 존재하지 않습니다."),
    POST_COMMENT_USER_MISMATCH(HttpStatus.BAD_REQUEST, "댓글 작성자와 일치하지 않습니다."),
    POST_COMMENT_POST_MISMATCH(HttpStatus.BAD_REQUEST, "해당 게시글에 존재하지 않는 댓글입니다."),

    // Influencer
    INFLUENCER_NOT_FOUND(HttpStatus.NOT_FOUND, "인플루언서 정보를 찾을 수 없습니다.")
    ;

    private final HttpStatus code;
    private final String info;
}
