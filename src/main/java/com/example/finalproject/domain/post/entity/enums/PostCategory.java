package com.example.finalproject.domain.post.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostCategory {
    NOTIFICATION("공지사항"),
    COMMUNITY_BUSINESS("커뮤니티(사업주)"),
    COMMUNITY_INFLUENCER("커뮤니티(인플루언서)"),
    EACH_OTHER_NEIGHBOR_FOLLOW("서이추/맞팔")
    ;

    private final String info;
}
