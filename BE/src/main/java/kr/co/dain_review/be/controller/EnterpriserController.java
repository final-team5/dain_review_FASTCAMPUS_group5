package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.user.EnterpriserUpdateUser;
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

import java.util.UUID;


@Api(tags = "사업자")
@RequestMapping("/ent")
@RequiredArgsConstructor
@RestController
public class EnterpriserController {

    private final CampaignService campaignService;
    private final TokenProvider tokenProvider;
    private final PostService postService;
    private final AlarmService alarmService;
    private final UserService userService;

    @ApiOperation(value = "프로필 수정", tags = "사용자 - 프로필")
    @PutMapping("/profile")
    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @RequestBody EnterpriserUpdateUser update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        userService.enterpriserProfileUpdate(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "내 체험단 검색", tags = "사업자 - 체험단")
    @GetMapping("/my-campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, CampaignSearch search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        json.put("list", campaignService.getList(search, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 모집", tags = "사업자 - 체험단")
    @PostMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        String id = String.valueOf(UUID.randomUUID());
        campaignService.setInsert(id, insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 진행 상세", tags = "사업자 - 체험단")
    @GetMapping("/campaign/{seq}")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        return new ResponseEntity<>(campaignService.getDetail(seq, userSeq), HttpStatus.OK);
    }

    @ApiOperation(value = "신청한 체험단 목록", tags = "사업자 - 체험단")
    @GetMapping("/applicationInfluencer/{seq}")
    public ResponseEntity<?> applicationInfluencer(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        if(!campaignService.myCampaignCheck(userSeq, seq)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", campaignService.applicationInfluencer(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "선정한 체험단 목록", tags = "사업자 - 체험단")
    @GetMapping("/selectionInfluencer/{seq}")
    public ResponseEntity<?> selectionInfluencer(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        if(!campaignService.myCampaignCheck(userSeq, seq)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", campaignService.selectionInfluencer(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 선정", tags = "사업자 - 체험단")
    @PostMapping("/select")
    public ResponseEntity<?> campaign_(@RequestHeader HttpHeaders header, @RequestBody InfluencerSelect select) {
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        if(campaignService.myCampaignCheck(userSeq, select.getCampaignSeq())) {
            campaignService.InfluencerSelect(select);
        }
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 진행하기", tags = "사업자 - 체험단")
    @PostMapping("/campaign/{seq}")
    public ResponseEntity<?> campaign_(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        campaignService.setProgress(seq, userSeq);
        alarmService.campaignProgress(seq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "신청 인플루언서 목록 다운로드", tags = "사업자 - 체험단")
    @PostMapping("/download/{seq}")
    public ResponseEntity<?> download(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        campaignService.setProgress(seq, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "커뮤니티 리스트", tags = "사업자 - 커뮤니티", notes = "searchType : myPost(내 글 보기), comment(댓글 단 글)")
    @GetMapping("/post")
    public ResponseEntity<?> post(@RequestHeader HttpHeaders header, Search search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", postService.select(search, 2, userSeq));
        json.put("totalCount", postService.selectCount(search, 2, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "사업자 - 커뮤니티")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> post(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", postService.selectDetail(seq, 2));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "사업자 - 커뮤니티")
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.insert(insert, 2, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "사업자 - 커뮤니티")
    @PutMapping("/post")
    public ResponseEntity<?> post(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.update(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "사업자 - 커뮤니티")
    @DeleteMapping("/post")
    public ResponseEntity<?> post(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.delete(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "결과보고서 (준비중)", tags = "사업자 - 체험단")
    @GetMapping("/result")
    public ResponseEntity<?> result(@RequestBody HttpHeaders header, @RequestBody Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
//        json.put("campaign", campaignService.getDetail(seq, userSeq));
//        json.put("summary", campaignService.getSummary(seq, userSeq));
//        json.put("performance", campaignService.getPerformance(seq, userSeq));
//        json.put("keywordView", campaignService.getKeywordView(seq, userSeq));
//        json.put("review", campaignService.getReview(seq, userSeq));
//        json.put("Keywords", campaignService.getKeywords(seq, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
