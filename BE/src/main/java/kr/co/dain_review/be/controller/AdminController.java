package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.transaction.TransactionUpdate;
import kr.co.dain_review.be.model.point.PointInsert;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.user.*;
import kr.co.dain_review.be.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "관리자")
@RequestMapping("/adm")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final UserService userService;
    private final PointService pointService;
    private final CommunityService communityService;
    private final CampaignService campaignService;
    private final AlarmService alarmService;
    private final PostService postService;
    private final TransactionService transactionService;

    //
    @ApiOperation(value = "인플루언서 리스트", tags = "관리자 - 인플루언서",
            notes = "searchType : nickname, name, email")
    @GetMapping("/influencer")
    public ResponseEntity<?> influencer(Search search){
        JSONObject json = new JSONObject();
        json.put("list", userService.getInfluencer(search));
        json.put("count", userService.getInfluencerCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="인플루언서 상세", tags = "관리자 - 인플루언서")
    @GetMapping("/influencer/{seq}")
    public ResponseEntity<?> influencer(@PathVariable Integer seq){
        return new ResponseEntity<>(userService.getInfluencerDetail(seq), HttpStatus.OK);
    }

    @ApiOperation(value ="인플루언서 추가", tags = "관리자 - 인플루언서")
    @PostMapping("/influencer")
    public ResponseEntity<?> influencer(@ModelAttribute AdminInfluencerInsert adminInsert){
        InfluencerInsert insert = new InfluencerInsert(adminInsert);
        userService.influencerInsert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="인플루언서 수정", tags = "관리자 - 인플루언서")
    @PutMapping("/influencer")
    public ResponseEntity<?> influencer(@ModelAttribute AdminInfluencerUpdate adminUpdate){
        InfluencerUpdate update = new InfluencerUpdate(adminUpdate);
        userService.influencerUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="인플루언서 삭제", tags = "관리자 - 인플루언서")
    @DeleteMapping("/influencer")
    public ResponseEntity<?> influencer(@RequestBody Delete delete){
        userService.setDelete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "사업자 리스트", tags = "관리자 - 사업자",
            notes = "searchType : nickname, name, email")
    @GetMapping("/businesses")
    public ResponseEntity<?> businesses(Search search){
        JSONObject json = new JSONObject();
        json.put("list", userService.getBusinesses(search));
        json.put("count", userService.getBusinessesCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="사업자 상세", tags = "관리자 - 사업자")
    @GetMapping("/businesses/{seq}")
    public ResponseEntity<?> businesses(@PathVariable Integer seq){
        return new ResponseEntity<>(userService.getBusinessesDetail(seq), HttpStatus.OK);
    }

    @ApiOperation(value ="사업자 추가", tags = "관리자 - 사업자")
    @PostMapping("/businesses")
    public ResponseEntity<?> businesses(@ModelAttribute AdminBusinessesInsert adminInsert){
        BusinessesInsert insert = new BusinessesInsert(adminInsert);
        userService.insertBusinesses(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="사업자 수정", tags = "관리자 - 사업자")
    @PutMapping("/businesses")
    public ResponseEntity<?> businesses(@ModelAttribute AdminBusinessesUpdate adminUpdate){
        BusinessesUpdate update = new BusinessesUpdate(adminUpdate);
        userService.businessesUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="사업자 삭제", tags = "관리자 - 사업자")
    @DeleteMapping("/businesses")
    public ResponseEntity<?> businesses(@RequestBody Delete delete){
        userService.setDelete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "광고 대행사 신청 리스트", tags = "관리자 - 광고대행사",
            notes = "searchType : nickname, name, email")
    @GetMapping("/agency")
    public ResponseEntity<?> agency(@PathVariable String type, Search search){
        JSONObject json = new JSONObject();
        json.put("list", userService.getList(search));
        json.put("count", userService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="광고대행사 상세", tags = "관리자 - 광고대행사")
    @GetMapping("/agency/{seq}")
    public ResponseEntity<?> agency(@PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("detail", userService.getDetail(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="광고대행사 승인|반려", tags = "관리자 - 광고대행사")
    @PutMapping("/agency")
    public ResponseEntity<?> agency(@RequestBody AgencyUpdate update){
//        userService.setUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "패널티 관리", tags = "관리자 - 인플루언서")
    @PostMapping("/penalty")
    public ResponseEntity<?> penalty(@RequestBody Penalty penalty){
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


//   포인트 관리
    @ApiOperation(value = "포인트 내역", tags = "관리자 - 포인트")
    @GetMapping("/point")
    public ResponseEntity<?> point(Search search){
        JSONObject json = new JSONObject();
        json.put("list", pointService.list(search));
        json.put("count", pointService.count(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
    
    @ApiOperation(value = "포인트 할당", tags = "관리자 - 포인트")
    @PutMapping("/point")
    public ResponseEntity<?> point(@RequestBody PointInsert insert){
        pointService.insert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

//    @ApiOperation(value = "포인트 상세", tags = "관리자 - 결제")
//    @GetMapping("/point/{seq}")
//    public ResponseEntity<?> point(@PathVariable Integer seq){
//        JSONObject json = new JSONObject();
//        json.put("detail", pointService.detail(seq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }


    @ApiOperation(value = "결제 내역", tags = "관리자 - 결제")
    @GetMapping("/payment")
    public ResponseEntity<?> payment(Search search){
        JSONObject json = new JSONObject();
        json.put("list", transactionService.list(search));
        json.put("count", transactionService.count(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "이체 확인", tags = "관리자 - 결제")
    @PutMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody TransactionUpdate update){
        transactionService.update(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }



    @ApiOperation(value = "공지 리스트", tags = "관리자 - 공지")
    @GetMapping("/post")
    public ResponseEntity<?> post(Search search){
        JSONObject json = new JSONObject();
        json.put("list", postService.list(search));
        json.put("count", postService.count(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "공지 상세", tags = "관리자 - 공지")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> post(@PathVariable Integer seq){
        return new ResponseEntity<>(postService.detail(seq), HttpStatus.OK);
    }


    @ApiOperation(value = "공지 추가", tags = "관리자 - 공지")
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody PostInsert insert){
        postService.insert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "공지 수정", tags = "관리자 - 게시판")
    @PutMapping("/post")
    public ResponseEntity<?> post(@RequestBody PostUpdate update){
        postService.update(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "공지 삭제", tags = "관리자 - 공지")
    @DeleteMapping("/post")
    public ResponseEntity<?> post(@RequestBody Delete delete){
        postService.delete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }




    //체험 관리
    @ApiOperation(value = "체험 리스트", tags = "관리자 - 체험단")
    @GetMapping("/campaign")
    public ResponseEntity<?> campaign(CampaignSearch search){
        JSONObject json = new JSONObject();
        json.put("list", campaignService.getList(search));
        json.put("count", campaignService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 상세", tags = "관리자 - 체험단")
    @GetMapping("/campaign/{id}")
    public ResponseEntity<?> campaign(@PathVariable String id){
        JSONObject json = new JSONObject();
        json.put("detail", campaignService.getDetail(id));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 추가", tags = "관리자 - 체험단")
    @PostMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestBody CampaignInsert insert){
        String id = String.valueOf(UUID.randomUUID());
        campaignService.setInsert(id, insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 수정", tags = "관리자 - 체험단")
    @PutMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestBody CampaignUpdate update){
        campaignService.setUpdate(update);
        String message = "";
        if(update.getStatus() == 2){
            message = update.getTitle()+" 체험단이 반려되었습니다. \n반려 사유 : "+update.getMessage();
        }
        else if (update.getStatus() == 3){
            message = update.getTitle()+" 체험단이 승인되었습니다.";
        }
        alarmService.insert(update.getCampaignSeq(), update.getUserSeq(), message);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 삭제", tags = "관리자 - 체험단")
    @DeleteMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestBody Delete delete){
        campaignService.setDelete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 미션 리스트", tags = "관리자 - 체험단")
    @GetMapping("/mission")
    public ResponseEntity<?> mission(){
        JSONObject json = new JSONObject();
        json.put("missions", campaignService.missions());
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 미션 추가", tags = "관리자 - 체험단")
    @PostMapping("/mission")
    public ResponseEntity<?> mission(@RequestBody MissionInsert insert){
        campaignService.insertMission(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 미션 삭제", tags = "관리자 - 체험단")
    @DeleteMapping("/mission")
    public ResponseEntity<?> mission(Delete delete){
        campaignService.deleteMission(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 미션 수정", tags = "관리자 - 체험단")
    @PutMapping("/mission")
    public ResponseEntity<?> mission(@RequestBody MissionUpdate update){
        campaignService.updateMission(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }




    //보고서 관리
//    @ApiOperation(value = "결과보고서 리스트", tags = "관리자 - 결과보고서")
//    @GetMapping("/report")
//    public ResponseEntity<?> report(Search search){
//        JSONObject json = new JSONObject();
//        json.put("list", campaignService.getList(search));
//        json.put("totalCount", campaignService.getListCount(search));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "결과보고서 상세", tags = "관리자 - 결과보고서")
//    @GetMapping("/report/{seq}")
//    public ResponseEntity<?> report(@PathVariable Integer seq){
//        JSONObject json = new JSONObject();
//        json.put("detail", campaignService.getDetail(seq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "결과보고서 추가", tags = "관리자 - 결과보고서")
//    @PostMapping("/report")
//    public ResponseEntity<?> report(@RequestBody ReportInsert insert){
//        campaignService.setInsert(insert);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "결과보고서 수정", tags = "관리자 - 결과보고서")
//    @PutMapping("/report")
//    public ResponseEntity<?> report(@RequestBody AdminReportUpdate update){
//        reportService.setUpdate(update);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "결과보고서 삭제", tags = "관리자 - 결과보고서")
//    @DeleteMapping("/report")
//    public ResponseEntity<?> report(@RequestBody Delete delete){
//        reportService.setDelete(delete);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }



//    ㄴㄴ체험단 모집 상태 관리
//    ㄴ통계 지표 확인
//    ㄴ알림
//    @ApiOperation(value = "알람 리스트", tags = "관리자 - 알림")
//    @GetMapping("/alarm")
//    public ResponseEntity<?> alarm(Search search){
//        JSONObject json = new JSONObject();
//        json.put("list", alarmService.getList(search));
//        json.put("totalCount", alarmService.getListCount(search));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "알람 추가", tags = "관리자 - 알림")
//    @PostMapping("/alarm")
//    public ResponseEntity<?> alarm(@RequestBody AdminAlarmInsert insert){
//        alarmService.setInsert(insert);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "알람 수정", tags = "관리자 - 알림")
//    @PutMapping("/alarm")
//    public ResponseEntity<?> alarm(@RequestBody AdminAlarmUpdate update){
//        alarmService.setUpdate(update);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "알람 삭제", tags = "관리자 - 알림")
//    @DeleteMapping("/alarm")
//    public ResponseEntity<?> alarm(@RequestBody Delete delete){
//        alarmService.setDelete(delete);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "패널티 부여", tags = "관리자 - 회원")
//    @PostMapping("/penalty")
//    public ResponseEntity<?> penalty(@RequestBody Penalty penalty){
//        String message = "다음 사유로 인해 페널티가 부여됩니다. 사유 : "+penalty.getMessage();
//        alarmService.insert(penalty.getCampaignSeq(), penalty.getUserSeq(), message);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

    @ApiOperation(value = "체험단 취소 요청 내역", tags = "관리자 - 체험단")
    @GetMapping("/cancel")
    public ResponseEntity<?> cancel(Search search){
        JSONObject json = new JSONObject();
        json.put("list", campaignService.cancelList(search));
        json.put("count", campaignService.cancelCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
