package com.example.finalproject.controller;

import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.campaign.dto.response.CampaignListResponse;
import com.example.finalproject.domain.campaign.entity.enums.FirstCampaignSearchType;
import com.example.finalproject.domain.campaign.entity.enums.SecondCampaignSearchType;
import com.example.finalproject.domain.campaign.service.CampaignService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "관리자")
@RequestMapping("/adm")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final CampaignService campaignService;

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
}
