package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.PostWithCommentsDto;
import com.example.finalproject.domain.post.dto.request.PostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.PostSaveRequest;
import com.example.finalproject.domain.post.dto.request.PostUpdateRequest;
import com.example.finalproject.domain.post.dto.response.PostDetailResponse;
import com.example.finalproject.domain.post.dto.response.PostListResponse;
import com.example.finalproject.domain.post.dto.response.PostResponse;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.post.entity.enums.SearchType;
import com.example.finalproject.domain.post.service.PostService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(tags = "인플루언서")
@RequestMapping(path = "/inf")
@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final PostService postService;

    @ApiOperation(value = "커뮤니티 글 추가", tags = "인플루언서 - 커뮤니티", notes = "category : QUESTION, KNOW_HOW, ACCOMPANY, ETC 중")
    @PostMapping(path = "/communities")
    public ResponseApi<PostResponse> saveInfCommunityPost(
            @Valid @RequestBody PostSaveRequest postSaveRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
            ) {
        PostDto postDto = postService.saveInfCommunityPost(postSaveRequest, userDetails.getUsername());
        PostResponse postResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping(path = "/communities")
    public ResponseApi<PostResponse> updateInfCommunityPost(
            @Valid @RequestBody PostUpdateRequest postUpdateRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostDto postDto = postService.updateInfCommunityPost(postUpdateRequest, userDetails.getUsername());
        PostResponse postResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping(path = "/communities")
    public ResponseApi<String> deleteInfCommunityPost(
            @Valid @RequestBody PostDeleteRequest postDeleteRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        postService.deletePost(postDeleteRequest.getSeq(), userDetails.getUsername());

        return ResponseApi.success(HttpStatus.OK, "influencer community post delete success");
    }

    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping(path = "/communities")
    public ResponseApi<Page<PostListResponse>> findListInfCommunityPost(
            @RequestParam(required = false) SearchType searchType,
            @ApiParam(value = "QUESTION, KNOW_HOW, ACCOMPANY, ETC 중 선택", allowableValues = "QUESTION, KNOW_HOW, ACCOMPANY, ETC") @RequestParam(required = false) PostType influencerSearchPostType,
            @RequestParam(required = false) String searchWord,
            @ApiIgnore @PageableDefault(sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<PostDto> listInfCommunityPost = postService.findListInfCommunityPost(searchType, influencerSearchPostType, searchWord, pageable);
        Page<PostListResponse> postListResponses = listInfCommunityPost.map(PostListResponse::from);

        return ResponseApi.success(HttpStatus.OK, postListResponses);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "인플루언서 - 커뮤니티")
    @GetMapping(path = "/communities/{seq}")
    public ResponseApi<PostDetailResponse> findDetailInfCommunityPost(
            @PathVariable Integer seq,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        PostWithCommentsDto postWithCommentsDto = postService.findDetailInfCommunityPost(seq, userDetails.getUsername(), pageable);
        postService.updateViewCounts(seq);

        PostDetailResponse detailResponse = PostDetailResponse.from(postWithCommentsDto);

        String nicknameOrCompanyName = postService.getNicknameOrCompanyName(detailResponse.getPostAuthor());
        detailResponse.setPostAuthor(nicknameOrCompanyName);

        return ResponseApi.success(HttpStatus.OK, detailResponse);
    }
}
