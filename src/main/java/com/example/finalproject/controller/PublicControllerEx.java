package com.example.finalproject.controller;

import com.example.finalproject.domain.user.dto.UserInfluencerDto;
import com.example.finalproject.domain.user.dto.request.InfluencerSignup;
import com.example.finalproject.domain.user.dto.response.UserInfluencerSaveResponse;
import com.example.finalproject.domain.user.service.UserService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "공용 - 임시")
@RequestMapping("/ex/api")      // TODO : 경로 수정 예정.
@RequiredArgsConstructor
@RestController
public class PublicControllerEx {

    private final UserService userService;

    @ApiOperation(value = "회원 가입(인플루언서)", tags = "공개 - 회원", notes = "signupSource : PORTAL_SEARCH, SNS, INTRODUCTION_TO_ACQUAINTANCES, ETC 중")
    @PostMapping(path = "/influencers/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseApi<?> influencers_signup(
        @ModelAttribute InfluencerSignup influencerSignup,
        @RequestPart(name = "profile", required = false) MultipartFile profile
    ) throws IOException {
        UserInfluencerDto userInfluencerDto = userService.registerInfluencer(influencerSignup, profile);
        UserInfluencerSaveResponse userInfluencerSaveResponse = UserInfluencerSaveResponse.from(userInfluencerDto);

        return ResponseApi.success(HttpStatus.OK, userInfluencerSaveResponse);
    }
}
