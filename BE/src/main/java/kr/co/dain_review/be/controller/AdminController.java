package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.alarm.AdminAlarmInsert;
import kr.co.dain_review.be.model.alarm.AdminAlarmUpdate;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.point.AdminPointUpdate;
import kr.co.dain_review.be.model.post.AdminPostInsert;
import kr.co.dain_review.be.model.post.AdminPostUpdate;
import kr.co.dain_review.be.model.product.ProductInsert;
import kr.co.dain_review.be.model.product.ProductSearch;
import kr.co.dain_review.be.model.product.ProductUpdate;
import kr.co.dain_review.be.model.user.AdminUserInsert;
import kr.co.dain_review.be.model.user.AdminUserUpdate;
import kr.co.dain_review.be.service.*;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "관리자")
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final UserService userService;
    private final PointService pointService;
    private final PostService postService;
    private final ProductService productService;
    private final AlarmService alarmService;

    //    ㄴ회원 관리
    @ApiOperation(value = "유저 리스트", tags = "관리자 - 회원")
    @GetMapping("/user")
    public ResponseEntity<?> user(Search search){
        JSONObject json = new JSONObject();
        json.put("list", userService.getList(search));
        json.put("totalCount", userService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="유저 상세", tags = "관리자 - 회원")
    @GetMapping("/user/{seq}")
    public ResponseEntity<?> user(@PathVariable Integer seq){

        JSONObject json = new JSONObject();
        json.put("detail", userService.getDetail(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="유저 추가", tags = "관리자 - 회원")
    @PostMapping("/user")
    public ResponseEntity<?> user(@RequestBody AdminUserInsert insert){
        userService.setInsert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="유저 수정", tags = "관리자 - 회원")
    @PutMapping("/user")
    public ResponseEntity<?> user(@RequestBody AdminUserUpdate update){
        userService.setUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value ="유저 삭제", tags = "관리자 - 회원")
    @DeleteMapping("/user")
    public ResponseEntity<?> user(@RequestBody Delete delete){
        userService.setDelete(delete);
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
        json.put("totalCount", pointService.listCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "포인트 상세", tags = "관리자 - 포인트")
    @GetMapping("/point/{seq}")
    public ResponseEntity<?> point(@PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("detail", pointService.detail(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "포인트 수정", tags = "관리자 - 포인트")
    @PutMapping("/point")
    public ResponseEntity<?> point(@RequestBody AdminPointUpdate update){
        pointService.update(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }





    @ApiOperation(value = "게시판 추가", tags = "관리자 - 게시판")
    @PostMapping("/post")
    public ResponseEntity<?> post(@RequestBody AdminPostInsert insert){
        postService.insertAdmin(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 수정", tags = "관리자 - 게시판")
    @PutMapping("/post")
    public ResponseEntity<?> post(@RequestBody AdminPostUpdate update){
        postService.updateAdmin(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "게시판 삭제", tags = "관리자 - 게시판")
    @DeleteMapping("/post")
    public ResponseEntity<?> post(@RequestBody Delete delete){
        postService.deleteAdmin(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }




    //체험 관리
    @ApiOperation(value = "체험 리스트", tags = "관리자 - 체험단")
    @GetMapping("/product")
    public ResponseEntity<?> product(ProductSearch search){
        JSONObject json = new JSONObject();
        json.put("list", productService.getList(search));
        json.put("totalCount", productService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 상세", tags = "관리자 - 체험단")
    @GetMapping("/product/{seq}")
    public ResponseEntity<?> product(@PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("detail", productService.getDetail(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 추가", tags = "관리자 - 체험단")
    @PostMapping("/product")
    public ResponseEntity<?> product(@RequestBody ProductInsert insert){
        productService.setInsert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 수정", tags = "관리자 - 체험단")
    @PutMapping("/product")
    public ResponseEntity<?> product(@RequestBody ProductUpdate update){
        productService.setUpdate(update);
        if(update.getMessage()!=null){
            alarmService.insert(update);
        }
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 삭제", tags = "관리자 - 체험단")
    @DeleteMapping("/product")
    public ResponseEntity<?> product(@RequestBody Delete delete){
        productService.setDelete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }




    //보고서 관리
//    @ApiOperation(value = "결과보고서 리스트", tags = "관리자 - 결과보고서")
//    @GetMapping("/report")
//    public ResponseEntity<?> report(Search search){
//        JSONObject json = new JSONObject();
//        json.put("list", productService.getList(search));
//        json.put("totalCount", productService.getListCount(search));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "결과보고서 상세", tags = "관리자 - 결과보고서")
//    @GetMapping("/report/{seq}")
//    public ResponseEntity<?> report(@PathVariable Integer seq){
//        JSONObject json = new JSONObject();
//        json.put("detail", productService.getDetail(seq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "결과보고서 추가", tags = "관리자 - 결과보고서")
//    @PostMapping("/report")
//    public ResponseEntity<?> report(@RequestBody ReportInsert insert){
//        productService.setInsert(insert);
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
    @ApiOperation(value = "알람 리스트", tags = "관리자 - 알림")
    @GetMapping("/alarm")
    public ResponseEntity<?> alarm(Search search){
        JSONObject json = new JSONObject();
        json.put("list", alarmService.getList(search));
        json.put("totalCount", alarmService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "알람 추가", tags = "관리자 - 알림")
    @PostMapping("/alarm")
    public ResponseEntity<?> alarm(@RequestBody AdminAlarmInsert insert){
        alarmService.setInsert(insert);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "알람 수정", tags = "관리자 - 알림")
    @PutMapping("/alarm")
    public ResponseEntity<?> alarm(@RequestBody AdminAlarmUpdate update){
        alarmService.setUpdate(update);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "알람 삭제", tags = "관리자 - 알림")
    @DeleteMapping("/alarm")
    public ResponseEntity<?> alarm(@RequestBody Delete delete){
        alarmService.setDelete(delete);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

}
