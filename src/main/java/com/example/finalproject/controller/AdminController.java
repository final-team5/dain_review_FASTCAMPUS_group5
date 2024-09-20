package com.example.finalproject.controller;

import com.example.finalproject.domain.alarm.dto.CampaignApproval;
import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.campaign.dto.response.CampaignListResponse;
import com.example.finalproject.domain.campaign.entity.enums.FirstCampaignSearchType;
import com.example.finalproject.domain.campaign.entity.enums.SecondCampaignSearchType;
import com.example.finalproject.domain.campaign.service.CampaignService;
import com.example.finalproject.domain.user.service.BusinessesService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "관리자")
@RequestMapping("/adm")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final CampaignService campaignService;

    private final BusinessesService businessesService;

    @ApiOperation(value = "체험 리스트", tags = "관리자 - 체험단")
    @GetMapping("/campaign")
    public ResponseApi<Page<CampaignListResponse>> findCampaignList(
            @ApiParam(value = "ALL, CAMPAIGN_TITLE, ID, COMPANY_NAME, PHONE_NUMBER 중 선택", allowableValues = "ALL, CAMPAIGN_TITLE, ID, COMPANY_NAME, PHONE_NUMBER") @RequestParam(required = false) FirstCampaignSearchType searchType1,
            @ApiParam(value = "READY, APPROVE, REFUSAL, CANCEL 중 선택", allowableValues = "READY, APPROVE, REFUSAL, CANCEL") @RequestParam(required = false) SecondCampaignSearchType searchType2,
            @RequestParam(required = false) String searchWord,
            @PageableDefault(sort = "registeredAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<CampaignDto> campaignPage = campaignService.findCampaignPage(searchType1, searchType2, searchWord, pageable);
        Page<CampaignListResponse> campaignListResponses = campaignPage.map(CampaignListResponse::from);

        return ResponseApi.success(HttpStatus.OK, campaignListResponses);
    }

    @ApiOperation(value = "체험 승인&반려", tags = "관리자 - 체험단", notes = "approval = 1 (승인), 0 (반려)")
    @PutMapping("/campaign-approval")
    public ResponseEntity<?> approval(@RequestBody CampaignApproval approval){
        campaignService.campaignApproval(approval);
        JSONObject json = new JSONObject();
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "대행사 승인&반려", tags = "관리자 - 대행사", notes = "status = 1 (승인), 2 (반려)")
    @PutMapping("/agency/approval")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "대행사 승인/반려 처리되었습니다."),
            @ApiResponse(code = 404, message = "해당 대행사 신청이 존재하지 않습니다.")
    })
    public ResponseApi<?> approveAgency(@RequestParam Integer agencySeq, @RequestParam Integer status) {
        businessesService.approveOrRejectAgency(agencySeq, status);
        return ResponseApi.success(HttpStatus.OK, "대행사 승인/반려 처리되었습니다.");
    }
}
