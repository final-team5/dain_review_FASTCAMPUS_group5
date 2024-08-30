package com.example.finalproject.domain.post.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {
    // 서이추/맞팔
    BLOG("블로그"),
    INSTAGRAM("인스타그램"),
    YOUTUBE("유튜브"),
    TIKTOK("틱톡"),

    // 인플루언서 커뮤니티
    QUESTION("질문하기"),
    KNOW_HOW("노하우"),
    ACCOMPANY("동행"),

    ETC("기타")

    ;

    private final String info;
}
