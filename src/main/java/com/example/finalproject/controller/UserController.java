package com.example.finalproject.controller;

import com.example.finalproject.domain.campaign.dto.CampaignPreferenceDto;
import com.example.finalproject.domain.campaign.dto.CampaignWithApplicantCountDto;
import com.example.finalproject.domain.campaign.dto.request.CampaignPreferenceRequest;
import com.example.finalproject.domain.campaign.dto.response.CampaignPreferenceListResponse;
import com.example.finalproject.domain.campaign.dto.response.CampaignPreferenceSaveResponse;
import com.example.finalproject.domain.campaign.service.CampaignService;
import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.request.*;
import com.example.finalproject.domain.post.dto.response.PostCommentResponse;
import com.example.finalproject.domain.post.dto.response.PostFollowDetailResponse;
import com.example.finalproject.domain.post.dto.response.PostFollowListResponse;
import com.example.finalproject.domain.post.dto.response.PostFollowResponse;
import com.example.finalproject.domain.post.entity.enums.SearchType;
import com.example.finalproject.domain.post.service.PostCommentService;
import com.example.finalproject.domain.post.service.PostService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = "회원")
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final PostCommentService postCommentService;
    private final PostService postService;
    private final CampaignService campaignService;

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


    @ApiOperation(value = "서이추/맞팔 글 추가", tags = "사용자 - 커뮤니티")
    @PostMapping(path = "/community")
    public ResponseApi<PostFollowResponse> saveFollowPost(
            @RequestBody PostFollowSaveRequest postFollowSaveRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        PostDto postDto = postService.saveFollowPost(postFollowSaveRequest.getCategory(), postFollowSaveRequest.getContents(), postFollowSaveRequest.getTitle(), userSeq);
        PostFollowResponse postFollowResponse = PostFollowResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postFollowResponse);
    }

    @ApiOperation(value = "서이추/맞팔 글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping(path = "/community")
    public ResponseApi<PostFollowResponse> updateFollowPost(
            @RequestBody PostFollowUpdateRequest postFollowUpdateRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        PostDto postDto = postService.updateFollowPost(postFollowUpdateRequest.getSeq(), postFollowUpdateRequest.getCategory(), postFollowUpdateRequest.getContents(), postFollowUpdateRequest.getTitle(), userSeq);
        PostFollowResponse postFollowUpdateResponse = PostFollowResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postFollowUpdateResponse);
    }

    @ApiOperation(value = "서이추/맞팔 글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping(path = "/communities")
    public ResponseApi<String> deleteFollowPost(
            @RequestBody PostFollowDeleteRequest postFollowDeleteRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        postService.deleteFollowPost(postFollowDeleteRequest.getSeq(), userSeq);

        return ResponseApi.success(HttpStatus.OK, "follow post delete success");
    }

    @ApiOperation(value = "서이추/맞팔 상세", tags = "사용자 - 커뮤니티")
    @GetMapping(path = "/community/{seq}")
    public ResponseApi<PostFollowDetailResponse> findDetailFollowPost(
            @PathVariable Integer seq,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        PostDto postDto = postService.findDetailFollowPost(seq, userSeq);
        postService.updateViewCounts(seq);

        PostFollowDetailResponse detailResponse = PostFollowDetailResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, detailResponse);
    }

    @ApiOperation(value = "서이추/맞팔 리스트", tags = "사용자 - 커뮤니티")
    @GetMapping(path = "/communities")
    public ResponseApi<Page<PostFollowListResponse>> findListFollowPost(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchWord,
            @PageableDefault(sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        Page<PostDto> listFollowPost = postService.findListFollowPost(searchType, searchWord, pageable);
        Page<PostFollowListResponse> postFollowListResponses = listFollowPost.map(PostFollowListResponse::from);

        return ResponseApi.success(HttpStatus.OK, postFollowListResponses);
    }

    @ApiOperation(value = "찜 하기", tags = "사용자 - 체험단")
    @PostMapping(path = "/favorites")
    public ResponseApi<CampaignPreferenceSaveResponse> saveCampaignPreference(
            @RequestBody CampaignPreferenceRequest campaignPreferenceRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        CampaignPreferenceDto campaignPreferenceDto = campaignService.saveCampaignPreference(campaignPreferenceRequest.getId(), userSeq);
        CampaignPreferenceSaveResponse campaignPreferenceSaveResponse = CampaignPreferenceSaveResponse.from(campaignPreferenceDto);

        return ResponseApi.success(HttpStatus.OK, campaignPreferenceSaveResponse);
    }

    @ApiOperation(value = "찜 제거", tags = "사용자 - 체험단")
    @DeleteMapping(path = "/favorites")
    public ResponseApi<String> deleteCampaignPreference(
            @RequestBody CampaignPreferenceRequest campaignPreferenceRequest,
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq
    ) {
        campaignService.deleteCampaignPreference(campaignPreferenceRequest.getId(), userSeq);

        return ResponseApi.success(HttpStatus.OK, "campaign preference delete success");
    }

    @ApiOperation(value = "찜 목록", tags = "사용자 - 체험단")
    @GetMapping(path = "/favorites")
    public ResponseApi<Page<CampaignPreferenceListResponse>> getCampaignPreferenceList(
            // TODO : security 도입 후 user 인자로 변경 예정
            Integer userSeq,
            Pageable pageable
    ) {
        Page<CampaignWithApplicantCountDto> campaignDtoPage = campaignService.getCampaignPreferenceList(userSeq, pageable);
        Page<CampaignPreferenceListResponse> campaignPreferenceListResponses = campaignDtoPage.map(CampaignPreferenceListResponse::from);

        return ResponseApi.success(HttpStatus.OK, campaignPreferenceListResponses);
    }
}
