package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.campaign.InfluencerApplication;
import kr.co.dain_review.be.model.campaign.CampaignSearch;
import kr.co.dain_review.be.model.campaign.ReportInsert;
import kr.co.dain_review.be.model.user.InfluencerUpdate;
import kr.co.dain_review.be.model.user.InfluencerUpdateUser;
import kr.co.dain_review.be.service.AlarmService;
import kr.co.dain_review.be.service.PostService;
import kr.co.dain_review.be.service.CampaignService;
import kr.co.dain_review.be.service.UserService;
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
    private final PostService postService;


    @ApiOperation(value = "프로필 수정", tags = "사용자 - 프로필")
    @PutMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @RequestBody InfluencerUpdateUser update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        userService.influencerProfileUpdate(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "내 체험단 검색", tags = "인플루언서 - 체험단")
    @GetMapping("/application")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, CampaignSearch search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", campaignService.applicationsList(search, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 신청", tags = "인플루언서 - 체험단")
    @PostMapping("/application")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody InfluencerApplication application){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        campaignService.application(application, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 신청 취소", tags = "인플루언서 - 체험단")
    @PostMapping("/cancellation")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        campaignService.cancellation(seq, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "찜 목록", tags = "인플루언서 - 체험단")
    @GetMapping("/favorites")
    public ResponseEntity<?> positive(@RequestHeader HttpHeaders header){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", campaignService.getFavoriteList(userSeq));
        json.put("totalCount", campaignService.getFavoriteListCount(userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 알림", tags = "인플루언서 - 체험단")
    @GetMapping("/alarm")
    public ResponseEntity<?> alarm(@RequestHeader HttpHeaders header){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", alarmService.getAlarmList(userSeq));
        json.put("totalCount", alarmService.getAlarmListCount(userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 리뷰", tags = "인플루언서 - 체험단")
    @PostMapping("/review")
    public ResponseEntity<?> report(@RequestHeader HttpHeaders header, @RequestBody ReportInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        String name = tokenProvider.getName(token);
        campaignService.review(insert, userSeq);
        alarmService.reviewAlarm(insert, name);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, Search search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", postService.select(search, 3, userSeq));
        json.put("totalCount", postService.selectCount(search, 3, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", postService.selectDetail(seq, 3));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "인플루언서 - 커뮤니티")
    @PostMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.insert(insert, 3, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.update(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.delete(delete, userSeq);
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
