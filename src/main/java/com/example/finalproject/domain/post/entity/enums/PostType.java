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
    ETC("기타")
    ;

    private final String info;
}
