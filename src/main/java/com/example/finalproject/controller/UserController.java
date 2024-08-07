package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.request.PostCommentRequest;
import com.example.finalproject.domain.post.service.PostCommentService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "회원")
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final PostCommentService postCommentService;

    public ResponseApi<String> savePostComment(
            @RequestBody PostCommentRequest postCommentRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        postCommentService.save(userSeq, postCommentRequest.getPostSeq(), postCommentRequest.getComment());

        return ResponseApi.success(HttpStatus.OK, "SUCCESS");
    }
}
