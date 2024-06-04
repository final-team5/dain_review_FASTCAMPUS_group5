package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.product.InfluencerApplication;
import kr.co.dain_review.be.model.product.ProductSearch;
import kr.co.dain_review.be.model.product.ReportInsert;
import kr.co.dain_review.be.service.AlarmService;
import kr.co.dain_review.be.service.PostService;
import kr.co.dain_review.be.service.ProductService;
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
    private final ProductService productService;
    private final UserService userService;
    private final AlarmService alarmService;
    private final PostService postService;

    @ApiOperation(value = "내 체험단 검색", tags = "인플루언서 - 체험단")
    @GetMapping("/application")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, ProductSearch search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", productService.applicationsList(search, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 신청", tags = "인플루언서 - 체험단")
    @PostMapping("/application")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, @RequestBody InfluencerApplication application){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.application(application, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 신청 취소", tags = "인플루언서 - 체험단")
    @PostMapping("/cancellation")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, @RequestBody Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.cancellation(seq, userSeq);
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
        json.put("list", productService.getFavoriteList(userSeq));
        json.put("totalCount", productService.getFavoriteListCount(userSeq));
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

    @ApiOperation(value = "체험 보고", tags = "인플루언서 - 체험단")
    @PostMapping("/review")
    public ResponseEntity<?> report(@RequestHeader HttpHeaders header, @RequestBody ReportInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.review(insert, userSeq);
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
//        json.put("schedule", productService.getSchedule(userSeq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

    

}
