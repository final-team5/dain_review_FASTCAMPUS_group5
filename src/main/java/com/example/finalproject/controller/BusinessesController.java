package com.example.finalproject.controller;

import com.example.finalproject.domain.campaign.dto.request.CampaignCancelRequest;
import com.example.finalproject.domain.campaign.dto.request.CampaignInsertRequest;
import com.example.finalproject.domain.campaign.dto.request.ReviewerSelectRequest;
import com.example.finalproject.domain.payment.dto.request.PaymentRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostUpdateRequest;
import com.example.finalproject.domain.user.dto.request.AgencyInsertRequest;
import com.example.finalproject.domain.user.service.BusinessesService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/bus")
@Api(tags = "사업자")
public class BusinessesController {

    private final BusinessesService businessesService;

    @Autowired
    public BusinessesController(BusinessesService businessesService) {
        this.businessesService = businessesService;
    }

    @ApiOperation(value = "대행사 신청", tags = "사업자 - 대행사")
    @PostMapping("/agency")
    public ResponseApi<?> applyAgency(@RequestBody AgencyInsertRequest insert) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        return ResponseApi.success(HttpStatus.CREATED, businessesService.agencyApplication(insert, userSeq));
    }

    @ApiOperation(value = "체험단 신규 모집", tags = "사업자 - 체험단")
    @PostMapping("/campaign")
    public ResponseApi<?> createCampaign(@RequestBody CampaignInsertRequest insert) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.createCampaign(insert, userSeq);
        return ResponseApi.success(HttpStatus.OK, "체험단 모집이 시작되었습니다.");
    }

    @ApiOperation(value = "검수 중인 체험단 취소", tags = "사업자 - 체험단")
    @DeleteMapping("/campaign")
    public ResponseApi<?> cancelCampaign(@RequestBody CampaignCancelRequest cancelRequest) {
        businessesService.cancelCampaign(cancelRequest);
        return ResponseApi.success(HttpStatus.OK, "체험단 취소 요청이 처리되었습니다.");
    }

    @ApiOperation(value = "체험단 진행 상세", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{id}")
    public ResponseApi<?> getCampaignDetail(@PathVariable String id) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        Object campaignDetail = businessesService.getCampaignDetail(id, userSeq);
        return ResponseApi.success(HttpStatus.OK, campaignDetail);
    }

    @ApiOperation(value = "체험 진행하기", tags = "사업자 - 체험단")
    @PostMapping("/campaign/{seq}")
    public ResponseApi<?> startCampaign(@PathVariable Integer seq) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.startCampaign(seq, userSeq);
        return ResponseApi.success(HttpStatus.OK, "체험단 진행이 시작되었습니다.");
    }

    @ApiOperation(value = "신청한 인플루언서 리스트", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{seq}/application")
    public ResponseApi<?> getApplicationInfluencerList(@PathVariable Integer seq) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        List<Object> influencerList = businessesService.getApplicationInfluencerList(seq, userSeq);
        return ResponseApi.success(HttpStatus.OK, influencerList);
    }

    @ApiOperation(value = "신청 인플루언서 목록 다운로드", tags = "사업자 - 체험단")
    @PostMapping("/campaign/{seq}/download")
    public ResponseApi<?> downloadInfluencerList(@PathVariable Integer seq) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        Object downloadLink = businessesService.downloadInfluencerList(seq, userSeq);
        return ResponseApi.success(HttpStatus.OK, downloadLink);
    }

    @ApiOperation(value = "선정한 체험단 리스트", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{seq}/selection")
    public ResponseApi<?> getSelectedInfluencerList(@PathVariable Integer seq) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        List<Object> selectedInfluencerList = businessesService.getSelectedInfluencerList(seq, userSeq);
        return ResponseApi.success(HttpStatus.OK, selectedInfluencerList);
    }

    @ApiOperation(value = "커뮤니티 리스트", tags = "사업자 - 커뮤니티")
    @GetMapping("/communities")
    public ResponseApi<?> getCommunityList(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "searchType", required = false) String searchType,
            @RequestParam(value = "searchWord", required = false) String searchWord) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        List<Object> communityList = businessesService.getCommunityList(page, searchType, searchWord, userSeq);
        return ResponseApi.success(HttpStatus.OK, communityList);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "사업자 - 커뮤니티")
    @PostMapping("/communities")
    public ResponseApi<?> addCommunityPost(@RequestBody CommunityPostRequest postRequest) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.addCommunityPost(postRequest, userSeq);
        return ResponseApi.success(HttpStatus.OK, "커뮤니티 글이 추가되었습니다.");
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "사업자 - 커뮤니티")
    @PutMapping("/communities")
    public ResponseApi<?> updateCommunityPost(@RequestBody CommunityPostUpdateRequest updateRequest) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.updateCommunityPost(updateRequest, userSeq);
        return ResponseApi.success(HttpStatus.OK, "커뮤니티 글이 성공적으로 수정되었습니다.");
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "사업자 - 커뮤니티")
    @DeleteMapping("/communities")
    public ResponseApi<?> deleteCommunityPosts(@RequestBody CommunityPostDeleteRequest deleteRequest) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.deleteCommunityPosts(deleteRequest, userSeq);
        return ResponseApi.success(HttpStatus.OK, "커뮤니티 글이 성공적으로 삭제되었습니다.");
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "사업자 - 커뮤니티")
    @GetMapping("/communities/{seq}")
    public ResponseApi<?> getCommunityDetail(@PathVariable Integer seq) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        Object communityDetail = businessesService.getCommunityDetail(seq, userSeq);
        return ResponseApi.success(HttpStatus.OK, communityDetail);
    }

    @ApiOperation(value = "포인트 충전", tags = "사업자 - 결제")
    @PostMapping("/deposits")
    public ResponseApi<?> deposits(@RequestBody PaymentRequest paymentRequest) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.deposits(paymentRequest, userSeq);
        return ResponseApi.success(HttpStatus.OK, "포인트가 충전되었습니다.");
    }

    @ApiOperation(value = "프로필 수정", tags = "사업자 - 프로필")
    @PutMapping("/profile")
    public ResponseApi<?> updateProfile(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "addressDetail", required = false) String addressDetail,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
            @RequestParam(value = "businessNumber", required = false) String businessNumber,
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "postalCode", required = false) Integer postalCode,
            @RequestParam(value = "profile", required = false) MultipartFile profile,
            @RequestParam(value = "pw", required = false) String pw,
            @RequestParam(value = "representative", required = false) String representative
    ) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.updateProfile(userSeq, address, addressDetail, attachment, businessNumber, company, email, name, phone, postalCode, profile, pw, representative);
        return ResponseApi.success(HttpStatus.OK, "프로필이 수정되었습니다.");
    }

    @ApiOperation(value = "리뷰어 선정", tags = "사업자 - 체험단")
    @PostMapping("/select")
    public ResponseApi<?> selectReviewer(@RequestBody ReviewerSelectRequest selectRequest) {
        // TODO: 사용자 인증 추가
        Integer userSeq = null;
        businessesService.selectReviewer(selectRequest, userSeq);
        return ResponseApi.success(HttpStatus.OK, "리뷰어가 성공적으로 선정되었습니다.");
    }
}