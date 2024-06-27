package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.main.ChangePassword;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostInsertCampaign;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.post.PostUpdateCampaign;
import kr.co.dain_review.be.model.user.User;
import kr.co.dain_review.be.service.PostService;
import kr.co.dain_review.be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@Api(tags = "회원")
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

//    @ApiOperation(value = "프로필 보기", tags = "사용자 - 프로필")
//    @GetMapping("/profile")
//    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header){
//        String token = header.getFirst("Authorization");
//        Integer userSeq = tokenProvider.getSeq(token);
//        JSONObject json = new JSONObject();
//        json.put("profile", userService.getProfile(userSeq));
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

//    @ApiOperation(value = "프로필 수정", tags = "사용자 - 프로필")
//    @PutMapping("/profile")
//    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @RequestBody UserUpdate update){
//        String token = header.getFirst("Authorization");
//        Integer userSeq = tokenProvider.getSeq(token);
//        userService.setProfile(update, userSeq);
//        JSONObject json = new JSONObject();
//        json.put("message", "SUCCESS");
//        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
//    }

    @ApiOperation(value = "회원 탈퇴", tags = "사용자 - 회원")
    @DeleteMapping("/withdrawal")
    public ResponseEntity<?> Withdrawal(@RequestHeader HttpHeaders header){

        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        userService.Withdrawal(userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }


    @ApiOperation(value = "토큰 갱신", tags = "사용자 - 회원")
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestHeader HttpHeaders header) throws ParseException {
        String token = header.getFirst("Authorization");
        JSONObject json = new JSONObject();
        Integer type = tokenProvider.getType(token);

        String email = tokenProvider.getEmail(token);
        User user = userService.getUser(email, type);
        json = setReturnValue(token, user);
        json.put("message", "토큰이 갱신되었습니다");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }


    @ApiOperation(value = "비밀번호 변경", tags = "사용자 - 회원")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestHeader HttpHeaders header, @RequestBody ChangePassword changePassword){

        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        String email = tokenProvider.getEmail(token);
        JSONObject json = new JSONObject();

        if(userService.checkSocialUser(email)){
            json.put("message", "소셜 가입 회원입니다.");
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUser(email, 1);
        if (!passwordEncoder.matches(changePassword.getPw(),  user.getPw())) {
            json.put("message", "기존 비밀번호가 일치하지 않습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
        }
        String encode = passwordEncoder.encode(changePassword.getNewPw());
        changePassword.setNewPw(encode);
        userService.changePassword(userSeq, changePassword);
        json.put("message", "비밀번호가 변경되었습니다");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }

    @ApiOperation(value = "서이추/맞팔 리스트", tags = "사용자 - 서이추/맞팔")
    @GetMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, Search search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("list", postService.select(search, 4, userSeq));
        json.put("totalCount", postService.selectCount(search, 4, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 상세", tags = "사용자 - 서이추/맞팔")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", postService.selectDetail(seq, 4));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 글 추가", tags = "사용자 - 서이추/맞팔")
    @PostMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.insert(insert, 3, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 글 수정", tags = "사용자 - 서이추/맞팔")
    @PutMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.update(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 글 삭제", tags = "사용자 - 서이추/맞팔")
    @DeleteMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.delete(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    public JSONObject setReturnValue(String token, User user) throws ParseException {
        String new_token = tokenProvider.createToken(user);
//        Date expireDate = tokenProvider.getExpireDate(new_token);
        JSONObject jo = new JSONObject();
        jo.put("token", new_token);
        jo.put("name", user.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNowDate = simpleDateFormat.format(tokenProvider.getExpireDate(new_token));
        jo.put("expireDate", strNowDate);
        return jo;
    }

    @ApiOperation(value = "체험단 글 추가", tags = "사용자 - 체험단")
    @PostMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody PostInsertCampaign insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.insertCampaign(insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 글 수정", tags = "사용자 - 체험단")
    @PutMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody PostUpdateCampaign update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.updateCampaign(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 글 삭제", tags = "사용자 - 체험단")
    @DeleteMapping("/campaign")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.deleteCampaign(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


}
