package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.request.PostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.PostSaveRequest;
import com.example.finalproject.domain.post.dto.request.PostUpdateRequest;
import com.example.finalproject.domain.post.dto.response.PostResponse;
import com.example.finalproject.domain.post.service.PostService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인플루언서")
@RequestMapping(path = "/inf")
@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final PostService postService;

    @ApiOperation(value = "커뮤니티 글 추가", tags = "인플루언서 - 커뮤니티", notes = "category : QUESTION, KNOW_HOW, ACCOMPANY, ETC 중")
    @PostMapping(path = "/communities")
    public ResponseApi<PostResponse> saveInfCommunityPost(
            @RequestBody PostSaveRequest postSaveRequest,
            // TODO : 인증 기능 추가 - 인플루언서인지 검증
            Integer userSeq
    ) {
        PostDto postDto = postService.saveInfCommunityPost(postSaveRequest, userSeq);
        PostResponse postResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping(path = "/communities")
    public ResponseApi<PostResponse> updateInfCommunityPost(
            @RequestBody PostUpdateRequest postUpdateRequest,
            // TODO : 인증 기능 추가 - 인플루언서인지 검증
            Integer userSeq
    ) {
        PostDto postDto = postService.updateInfCommunityPost(postUpdateRequest, userSeq);
        PostResponse postResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping(path = "/communities")
    public ResponseApi<String> deleteInfCommunityPost(
            @RequestBody PostDeleteRequest postDeleteRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        postService.deletePost(postDeleteRequest.getSeq(), userSeq);

        return ResponseApi.success(HttpStatus.OK, "influencer community post delete success");
    }
}
