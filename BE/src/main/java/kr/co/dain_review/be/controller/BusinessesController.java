package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.transaction.AccountInfo;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.transaction.Payment;
import kr.co.dain_review.be.model.user.AgencyInsert;
import kr.co.dain_review.be.model.user.BusinessesUpdate;
import kr.co.dain_review.be.model.user.UserBusinessesUpdate;
import kr.co.dain_review.be.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Api(tags = "사업자")
@RequestMapping("/bus")
@RequiredArgsConstructor
@RestController
public class BusinessesController {


    private final CampaignService campaignService;
    private final TokenProvider tokenProvider;
    private final CommunityService communityService;
    private final UserService userService;
    private final TransactionService transactionService;

    @ApiOperation(value = "프로필 수정", tags = "사업자 - 프로필")
    @PutMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @ModelAttribute UserBusinessesUpdate userUpdate){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        BusinessesUpdate update = new BusinessesUpdate(userUpdate, userSeq);
        userService.businessesProfileUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "포인트 충전", tags = "사업자 - 결제")
    @PostMapping("/deposits")
    public ResponseEntity<?> deposits(@RequestHeader HttpHeaders header, @RequestBody Payment payment){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        transactionService.deposits(payment, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 모집 신청", tags = "사업자 - 체험단")
    @PostMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        String id = String.valueOf(UUID.randomUUID());
        campaignService.setInsert(id, insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "검수&모집 중인 체험단 삭제", tags = "사업자 - 체험단")
    @DeleteMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignId target){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.isCampaignDelete(target.getId())){
            json.put("message", "이미 모집 완료 혹은 리뷰 중인 캠페인이라 삭제가 불가능 합니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        campaignService.deleteCampaign(target.getId(), userSeq);
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "모집완료&리뷰 중인 체험단 취소 요청", tags = "사업자 - 체험단")
    @PutMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @ModelAttribute CancelInsert cancel){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.cancelRequestCheck(cancel.getCampaignId(), userSeq)){
            json.put("message", "이미 신청한 취소 요청입니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        cancel.setIsHost(1);
        campaignService.cancellation2(cancel, userSeq);
        campaignService.campaignSimpleCancellation(cancel, userSeq);
        campaignService.campaignCancel(cancel.getCampaignId(), userSeq);

        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "체험단 진행 상세", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @PathVariable String campaignId){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        return new ResponseEntity<>(campaignService.getDetail(campaignId, userSeq), HttpStatus.OK);
    }

    @ApiOperation(value = "신청한 인플루언서 리스트", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{campaignId}/application")
    public ResponseEntity<?> applicationInfluencer(@RequestHeader HttpHeaders header, @PathVariable String campaignId){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.myCampaignCheck(userSeq, campaignId)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", campaignService.applicationInfluencer(campaignId));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "선정한 체험단 리스트", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{campaignId}/selection")
    public ResponseEntity<?> selectionInfluencer(@RequestHeader HttpHeaders header, @PathVariable String campaignId){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.myCampaignCheck(userSeq, campaignId)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", campaignService.selectionInfluencer(campaignId));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "리뷰어 선정", tags = "사업자 - 체험단")
    @PostMapping("/select")
    public ResponseEntity<?> campaign_(@RequestHeader HttpHeaders header, @RequestBody InfluencerSelect select) {
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.myCampaignCheck(userSeq, select.getCampaignId())){
            json.put("message", "해당 캠페인을 수정하실 수 없습니다.");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        if(campaignService.campaignRecruiterCheck(select)){
            json.put("message", "캠페인 모집 인원을 초과하였습니다.");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        campaignService.influencerSelect(select);
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 모집 완료", tags = "사업자 - 체험단")
    @PutMapping("/campaign/completeRecruitment")
    public ResponseEntity<?> campaign_(@RequestHeader HttpHeaders header, @RequestBody CampaignId campaign){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.myCampaignCheck(userSeq, campaign.getId())){
            json.put("message", "해당 캠페인을 수정하실 수 없습니다.");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        campaignService.completeRecruitment(campaign.getId());
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



    @ApiOperation(value = "커뮤니티 리스트", tags = "사업자 - 커뮤니티", notes = "searchType : myPost(내 글 보기)")
    @GetMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, Search search){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        json.put("list", communityService.list(search, 2, userSeq));
        json.put("count", communityService.count(search, 2, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "사업자 - 커뮤니티")
    @GetMapping("/communities/{seq}")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        return new ResponseEntity<>(communityService.detail(seq, 2), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "사업자 - 커뮤니티")
    @PostMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.insert(insert, 2, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "사업자 - 커뮤니티")
    @PutMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.update(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "사업자 - 커뮤니티")
    @DeleteMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.delete(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "결과보고서 (준비중)", tags = "사업자 - 체험단")
    @GetMapping("/result")
    public ResponseEntity<?> result(@RequestBody HttpHeaders header, @RequestBody Integer seq){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
//        json.put("campaign", campaignService.getDetail(seq, userSeq));
//        json.put("summary", campaignService.getSummary(seq, userSeq));
//        json.put("performance", campaignService.getPerformance(seq, userSeq));
//        json.put("keywordView", campaignService.getKeywordView(seq, userSeq));
//        json.put("review", campaignService.getReview(seq, userSeq));
//        json.put("Keywords", campaignService.getKeywords(seq, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "대행사 신청", tags = "사업자 - 대행사")
    @PostMapping("/agency")
    public ResponseEntity<?> agency(@RequestBody HttpHeaders header, @RequestBody AgencyInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        userService.agencyApplication(insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
