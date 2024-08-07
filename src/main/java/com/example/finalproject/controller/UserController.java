package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.dto.request.PostCommentSaveRequest;
import com.example.finalproject.domain.post.dto.response.PostCommentResponse;
import com.example.finalproject.domain.post.service.PostCommentService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "회원")
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final PostCommentService postCommentService;

    @ApiOperation(value = "커뮤니티 댓글 추가", tags = "사용자 - 커뮤니티")
    @PostMapping(path = "/community/comments")
    public ResponseApi<PostCommentResponse> savePostComment(
            @RequestBody PostCommentSaveRequest postCommentSaveRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        PostCommentDto postCommentDto = postCommentService.save(userSeq, postCommentSaveRequest.getPostSeq(), postCommentSaveRequest.getComment());
        PostCommentResponse postCommentResponse = PostCommentResponse.from(postCommentDto);

        return ResponseApi.success(HttpStatus.OK, postCommentResponse);
    }
}
