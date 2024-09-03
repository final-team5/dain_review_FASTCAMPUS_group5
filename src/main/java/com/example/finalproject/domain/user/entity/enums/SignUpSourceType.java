package com.example.finalproject.domain.user.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignUpSourceType {

    PORTAL_SEARCH("포털 검색"),
    SNS("SNS"),
    INTRODUCTION_TO_ACQUAINTANCES("지인 소개"),
    ETC("기타")

    ;

    private final String info;
}