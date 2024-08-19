package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.dto.request.PostCommentDeleteRequest;
import com.example.finalproject.domain.post.dto.request.PostCommentSaveRequest;
import com.example.finalproject.domain.post.dto.request.PostCommentUpdateRequest;
import com.example.finalproject.domain.post.dto.response.PostCommentResponse;
import com.example.finalproject.domain.post.service.PostCommentService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        PostCommentDto postCommentDto = postCommentService.save(userSeq, postCommentSaveRequest.getPostSeq(), postCommentSaveRequest.getCommentSeq(), postCommentSaveRequest.getComment());
        PostCommentResponse postCommentResponse = PostCommentResponse.from(postCommentDto);

        return ResponseApi.success(HttpStatus.OK, postCommentResponse);
    }

    @ApiOperation(value = "커뮤니티 댓글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping(path = "/community/comments")
    public ResponseApi<PostCommentResponse> updatePostComment(
            @RequestBody PostCommentUpdateRequest postCommentUpdateRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        PostCommentDto postCommentDto = postCommentService.update(userSeq, postCommentUpdateRequest.getPostSeq(), postCommentUpdateRequest.getPostCommentSeq(), postCommentUpdateRequest.getComment());
        PostCommentResponse postCommentResponse = PostCommentResponse.from(postCommentDto);

        return ResponseApi.success(HttpStatus.OK, postCommentResponse);
    }

    @ApiOperation(value = "커뮤니티 댓글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping(path = "/community/comments")
    public ResponseApi<String> deletePostComment(
            @RequestBody PostCommentDeleteRequest postCommentDeleteRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        postCommentService.delete(postCommentDeleteRequest.getPostCommentSeq(), userSeq);

        return ResponseApi.success(HttpStatus.OK, "comment delete success");
    }

    // TODO : 인플루언서, 사업자 커뮤니티 상세 조회 기능과 겹치는 것 같아 삭제 될 수 있음.
    @ApiOperation(value = "커뮤니티 댓글 리스트 조회", tags = "사용자 - 커뮤니티")
    @GetMapping(path = "/community/comments/{postSeq}")
    public ResponseApi<Page<PostCommentResponse>> getPostComments(
            // TODO : security 도입 후 user 인자 추가 예정
            @PathVariable Integer postSeq,
            Pageable pageable
    ) {
        Page<PostCommentDto> commentDtoPage = postCommentService.getComments(postSeq, pageable);
        Page<PostCommentResponse> postCommentResponsePage = commentDtoPage.map(PostCommentResponse::from);

        return ResponseApi.success(HttpStatus.OK, postCommentResponsePage);
    }
}
