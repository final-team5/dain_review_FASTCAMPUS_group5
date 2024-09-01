package com.example.finalproject.domain.post.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostType {
    BLOG("블로그"),
    INSTAGRAM("인스타그램"),
    YOUTUBE("유튜브"),
    TIKTOK("틱톡"),

    // 인플루언서 게시글 생성 시 카테고리 항목
    QUESTION("질문하기"),
    TIPS("노하우"),
    PARTNER("동행"),

    ETC("기타")
    ;

    private final String info;
}
