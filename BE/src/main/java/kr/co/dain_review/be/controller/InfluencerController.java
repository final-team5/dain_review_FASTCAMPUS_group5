package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.transaction.AccountInfo;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.user.InfluencerUpdate;
import kr.co.dain_review.be.model.user.UserInfluencerUpdate;
import kr.co.dain_review.be.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "인플루언서")
@RequestMapping("/inf")
@RequiredArgsConstructor
@RestController
public class InfluencerController {

    private final TokenProvider tokenProvider;
    private final CampaignService campaignService;
    private final UserService userService;
    private final AlarmService alarmService;
    private final CommunityService communityService;
    private final TransactionService transactionService;


    @ApiOperation(value = "프로필 수정", tags = "사용자 - 프로필")
    @PutMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @ModelAttribute UserInfluencerUpdate userUpdate){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        InfluencerUpdate update = new InfluencerUpdate(userUpdate, userSeq);
        userService.influencerUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "체험 신청", tags = "인플루언서 - 체험단")
    @PostMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody InfluencerApplication application){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        if(campaignService.checkApplication(application.getCampaignSeq(), userSeq)){
            json.put("message", "이미 신청하신 체험단 입니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        campaignService.application(application, userSeq);
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "일반 체험단 신청 취소", tags = "인플루언서 - 체험단")
    @DeleteMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignId target){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.cancellation(target.getId(), userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "진행중인 체험단 신청 취소", tags = "인플루언서 - 체험단")
    @PutMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @ModelAttribute Cancel cancel){
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
        campaignService.cancellation2(cancel, userSeq);
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "진행중인 체험단 리스트", tags = "인플루언서 - 체험단")
    @GetMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        return new ResponseEntity<>(campaignService.influencerCampaign(userSeq), HttpStatus.OK);
    }


    @ApiOperation(value = "체험 리뷰 등록", tags = "인플루언서 - 체험단")
    @PostMapping("/review")
    public ResponseEntity<?> review(@RequestHeader HttpHeaders header, @RequestBody ReportInsert insert){
        Integer userSeq = null;
        String name = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
            name = tokenProvider.getName(token);
        }
        campaignService.review(insert, userSeq);
        alarmService.reviewAlarm(insert, name);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, Search search){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        json.put("list", communityService.list(search, 3, userSeq));
        json.put("count", communityService.count(search, 3, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/communities/{seq}")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", communityService.detail(seq, 3));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "인플루언서 - 커뮤니티")
    @PostMapping("/communities")
    public ResponseEntity<?> communities(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.insert(insert, 3, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
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

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
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

    @ApiOperation(value = "출금 신청", tags = "인플루언서 - 포인트")
    @PostMapping("/withdrawals")
    public ResponseEntity<?> payment(@RequestHeader HttpHeaders header, @RequestBody AccountInfo info){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        transactionService.withdrawals(info, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

//    @ApiOperation(value = "일정표", tags = "인플루언서 - 체험")
//    @PostMapping("/schedule")
//    public ResponseEntity<?> schedule(@RequestHeader HttpHeaders header){
//        String token = header.getFirst("Authorization");
//        Integer userSeq = tokenProvider.getSeq(token);
//        JSONObject json = new JSONObject();
//        json.put("schedule", campaignService.getSchedule(userSeq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

    

}
