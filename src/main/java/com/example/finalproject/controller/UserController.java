package com.example.finalproject.controller;

import com.example.finalproject.domain.campaign.dto.CampaignPreferenceDto;
import com.example.finalproject.domain.campaign.dto.CampaignWithApplicantCountDto;
import com.example.finalproject.domain.campaign.dto.request.CampaignPreferenceRequest;
import com.example.finalproject.domain.campaign.dto.response.CampaignPreferenceListResponse;
import com.example.finalproject.domain.campaign.dto.response.CampaignPreferenceSaveResponse;
import com.example.finalproject.domain.campaign.service.CampaignService;
import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.PostWithCommentsDto;
import com.example.finalproject.domain.post.dto.request.*;
import com.example.finalproject.domain.post.dto.response.PostCommentResponse;
import com.example.finalproject.domain.post.dto.response.PostDetailResponse;
import com.example.finalproject.domain.post.dto.response.PostListResponse;
import com.example.finalproject.domain.post.dto.response.PostResponse;
import com.example.finalproject.domain.post.entity.enums.SearchType;
import com.example.finalproject.domain.post.service.PostCommentService;
import com.example.finalproject.domain.post.service.PostService;
import com.example.finalproject.domain.user.dto.request.ChangePasswordRequest;
import com.example.finalproject.domain.user.service.UserService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Api(tags = "회원")
@RequestMapping(path = "/user")
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final PostCommentService postCommentService;
    private final PostService postService;
    private final CampaignService campaignService;
    private final UserService userService;

    @ApiOperation(value = "커뮤니티 댓글 추가", tags = "사용자 - 커뮤니티")
    @PostMapping(path = "/community/comments")
    public ResponseApi<PostCommentResponse> savePostComment(
            @Valid @RequestBody PostCommentSaveRequest postCommentSaveRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostCommentDto postCommentDto = postCommentService.save(userDetails.getUsername(), postCommentSaveRequest.getPostSeq(), postCommentSaveRequest.getCommentSeq(), postCommentSaveRequest.getComment());
        PostCommentResponse postCommentResponse = PostCommentResponse.from(postCommentDto);

        return ResponseApi.success(HttpStatus.OK, postCommentResponse);
    }

    @ApiOperation(value = "커뮤니티 댓글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping(path = "/community/comments")
    public ResponseApi<PostCommentResponse> updatePostComment(
            @Valid @RequestBody PostCommentUpdateRequest postCommentUpdateRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostCommentDto postCommentDto = postCommentService.update(userDetails.getUsername(), postCommentUpdateRequest.getPostSeq(), postCommentUpdateRequest.getPostCommentSeq(), postCommentUpdateRequest.getComment());
        PostCommentResponse postCommentResponse = PostCommentResponse.from(postCommentDto);

        return ResponseApi.success(HttpStatus.OK, postCommentResponse);
    }

    @ApiOperation(value = "커뮤니티 댓글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping(path = "/community/comments")
    public ResponseApi<String> deletePostComment(
            @Valid @RequestBody PostCommentDeleteRequest postCommentDeleteRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        postCommentService.delete(postCommentDeleteRequest.getPostCommentSeq(), userDetails.getUsername());

        return ResponseApi.success(HttpStatus.OK, "comment delete success");
    }


    @ApiOperation(value = "서이추/맞팔 글 추가", tags = "사용자 - 커뮤니티", notes = "category : BLOG, INSTAGRAM, TIKTOK, YOUTUBE, ETC 중")
    @PostMapping(path = "/community")
    public ResponseApi<PostResponse> saveFollowPost(
            @Valid @RequestBody PostSaveRequest postSaveRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostDto postDto = postService.saveFollowPost(postSaveRequest.getCategory(), postSaveRequest.getContents(), postSaveRequest.getTitle(), userDetails.getUsername());
        PostResponse postResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postResponse);
    }

    @ApiOperation(value = "서이추/맞팔 글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping(path = "/community")
    public ResponseApi<PostResponse> updateFollowPost(
            @Valid @RequestBody PostUpdateRequest postUpdateRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        PostDto postDto = postService.updateFollowPost(postUpdateRequest.getSeq(), postUpdateRequest.getCategory(), postUpdateRequest.getContents(), postUpdateRequest.getTitle(), userDetails.getUsername());
        PostResponse postFollowUpdateResponse = PostResponse.from(postDto);

        return ResponseApi.success(HttpStatus.OK, postFollowUpdateResponse);
    }

    @ApiOperation(value = "서이추/맞팔 글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping(path = "/communities")
    public ResponseApi<String> deleteFollowPost(
            @Valid @RequestBody PostDeleteRequest postDeleteRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        postService.deletePost(postDeleteRequest.getSeq(), userDetails.getUsername());

        return ResponseApi.success(HttpStatus.OK, "follow post delete success");
    }

    @ApiOperation(value = "서이추/맞팔 상세", tags = "사용자 - 커뮤니티")
    @GetMapping(path = "/community/{seq}")
    public ResponseApi<PostDetailResponse> findDetailFollowPost(
            @PathVariable Integer seq,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        PostWithCommentsDto postWithCommentsDto = postService.findDetailFollowPost(seq, userDetails.getUsername(), pageable);
        postService.updateViewCounts(seq);

        PostDetailResponse detailResponse = PostDetailResponse.from(postWithCommentsDto);

        String nicknameOrCompanyName = postService.getNicknameOrCompanyName(detailResponse.getPostAuthor());
        detailResponse.setPostAuthor(nicknameOrCompanyName);

        return ResponseApi.success(HttpStatus.OK, detailResponse);
    }

    @ApiOperation(value = "서이추/맞팔 리스트", tags = "사용자 - 커뮤니티")
    @GetMapping(path = "/communities")
    public ResponseApi<Page<PostListResponse>> findListFollowPost(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchWord,
            @PageableDefault(sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        Page<PostDto> listFollowPost = postService.findListFollowPost(searchType, searchWord, pageable);
        Page<PostListResponse> postFollowListResponses = listFollowPost.map(PostListResponse::from);

        return ResponseApi.success(HttpStatus.OK, postFollowListResponses);
    }

    @ApiOperation(value = "찜 하기", tags = "사용자 - 체험단")
    @PostMapping(path = "/favorites")
    public ResponseApi<CampaignPreferenceSaveResponse> saveCampaignPreference(
            @Valid @RequestBody CampaignPreferenceRequest campaignPreferenceRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        CampaignPreferenceDto campaignPreferenceDto = campaignService.saveCampaignPreference(campaignPreferenceRequest.getId(), userDetails.getUsername());
        CampaignPreferenceSaveResponse campaignPreferenceSaveResponse = CampaignPreferenceSaveResponse.from(campaignPreferenceDto);

        return ResponseApi.success(HttpStatus.OK, campaignPreferenceSaveResponse);
    }

    @ApiOperation(value = "찜 제거", tags = "사용자 - 체험단")
    @DeleteMapping(path = "/favorites")
    public ResponseApi<String> deleteCampaignPreference(
            @Valid @RequestBody CampaignPreferenceRequest campaignPreferenceRequest,
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        campaignService.deleteCampaignPreference(campaignPreferenceRequest.getId(), userDetails.getUsername());

        return ResponseApi.success(HttpStatus.OK, "campaign preference delete success");
    }

    @ApiOperation(value = "찜 목록", tags = "사용자 - 체험단")
    @GetMapping(path = "/favorites")
    public ResponseApi<Page<CampaignPreferenceListResponse>> getCampaignPreferenceList(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable
    ) {
        Page<CampaignWithApplicantCountDto> campaignDtoPage = campaignService.getCampaignPreferenceList(userDetails.getUsername(), pageable);
        Page<CampaignPreferenceListResponse> campaignPreferenceListResponses = campaignDtoPage.map(CampaignPreferenceListResponse::from);

        return ResponseApi.success(HttpStatus.OK, campaignPreferenceListResponses);
    }

    @ApiOperation(value = "비밀번호 변경", tags = "사용자 - 회원")
    @PutMapping(path = "/change-password")
    public ResponseApi<String> changePassword(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        userService.changePassword(userDetails.getUsername(), changePasswordRequest);

        return ResponseApi.success(HttpStatus.OK, "password change success");
    }

    @ApiOperation(value = "회원 탈퇴", tags = "사용자 - 회원")
    @DeleteMapping(path = "/withdrawal")
    public ResponseApi<String> withdrawalUser(
            @ApiIgnore @AuthenticationPrincipal UserDetails userDetails
    ) {
        userService.withdrawalUser(userDetails.getUsername());

        return ResponseApi.success(HttpStatus.OK, "user withdrawal success");
    }
}
