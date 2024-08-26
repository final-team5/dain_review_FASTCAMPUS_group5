package com.example.finalproject.domain.post.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {
    ALL("전체"),
    TITLE("제목"),
    CONTENTS("내용"),
    USER("작성자")
    ;

    private final String info;
}
