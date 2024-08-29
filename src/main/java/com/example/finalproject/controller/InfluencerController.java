package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.request.InfCommunitySaveRequest;
import com.example.finalproject.domain.post.dto.response.PostFollowResponse;
import com.example.finalproject.domain.post.service.PostService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "인플루언서")
@RequestMapping(path = "/inf")
@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final PostService postService;

    @ApiOperation(value = "커뮤니티 글 추가", tags = "인플루언서 - 커뮤니티")
    @PostMapping(path = "/communities")
    public ResponseApi<PostFollowResponse> saveInfCommunityPost(
            @RequestBody InfCommunitySaveRequest infCommunitySaveRequest,
            // TODO : 인증 기능 추가 - 인플루언서인지 검증
            Integer userSeq
    ) {
        PostDto postDto = postService.saveInfCommunityPost(infCommunitySaveRequest, userSeq);
        PostFollowResponse postResponse = PostFollowResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }
}
