package com.example.finalproject.domain.post.entity.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ApiModel(description = "PostType Enum")
@Getter
@RequiredArgsConstructor
public enum PostType {
    // 서이추/맞팔
    @ApiModelProperty(value = "블로그")
    BLOG("블로그"),

    @ApiModelProperty(value = "인스타그램")
    INSTAGRAM("인스타그램"),

    @ApiModelProperty(value = "유튜브")
    YOUTUBE("유튜브"),

    @ApiModelProperty(value = "틱톡")
    TIKTOK("틱톡"),

    // 인플루언서 커뮤니티
    @ApiModelProperty(value = "질문하기")
    QUESTION("질문하기"),

    @ApiModelProperty(value = "노하우")
    KNOW_HOW("노하우"),

    @ApiModelProperty(value = "동행")
    ACCOMPANY("동행"),

    @ApiModelProperty(value = "기타")
    ETC("기타")

    ;

    private final String info;
}
