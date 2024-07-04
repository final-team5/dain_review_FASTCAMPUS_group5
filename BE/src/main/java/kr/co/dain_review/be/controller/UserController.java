package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.campaign.CampaignCommentsInsert;
import kr.co.dain_review.be.model.campaign.CampaignCommentsUpdate;
import kr.co.dain_review.be.model.campaign.CampaignId;
import kr.co.dain_review.be.model.campaign.CampaignSeq;
import kr.co.dain_review.be.model.community.CommunityCommentsInsert;
import kr.co.dain_review.be.model.community.CommunityCommentsUpdate;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.main.ChangePassword;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.user.User;
import kr.co.dain_review.be.service.AlarmService;
import kr.co.dain_review.be.service.CampaignService;
import kr.co.dain_review.be.service.CommunityService;
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
    private final CommunityService communityService;
    private final CampaignService campaignService;
    private final AlarmService alarmService;


    @ApiOperation(value = "회원 탈퇴", tags = "사용자 - 회원")
    @DeleteMapping("/withdrawal")
    public ResponseEntity<?> Withdrawal(@RequestHeader HttpHeaders header){

        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
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

        Integer userSeq = null;
        String email = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
            email = tokenProvider.getEmail(token);
        }
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

    @ApiOperation(value = "서이추/맞팔 리스트", tags = "사용자 - 커뮤니티")
    @GetMapping("/communities")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, Search search){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        json.put("list", communityService.list(search, 4, userSeq));
        json.put("count", communityService.count(search, 4, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 상세", tags = "사용자 - 커뮤니티")
    @GetMapping("/community/{seq}")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", communityService.detail(seq, 4));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "서이추/맞팔 글 추가", tags = "사용자 - 커뮤니티")
    @PostMapping("/community")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
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

    @ApiOperation(value = "서이추/맞팔 글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping("/community")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
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

    @ApiOperation(value = "서이추/맞팔 글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping("/communities")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
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


    @ApiOperation(value = "커뮤니티 댓글 추가", tags = "사용자 - 커뮤니티")
    @PostMapping("/community/comments")
    public ResponseEntity<?> insertCommunityComments(@RequestHeader HttpHeaders header, @RequestBody CommunityCommentsInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.insertCommunityComments(insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 댓글 수정", tags = "사용자 - 커뮤니티")
    @PutMapping("/community/comments")
    public ResponseEntity<?> updateCommunityComments(@RequestHeader HttpHeaders header, @RequestBody CommunityCommentsUpdate update){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.updateCommunityComments(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 댓글 삭제", tags = "사용자 - 커뮤니티")
    @DeleteMapping("/community/comments")
    public ResponseEntity<?> deleteCommunityComments(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        communityService.deleteCommunityComments(delete, userSeq);
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

    @ApiOperation(value = "체험단 댓글 추가", tags = "사용자 - 체험단")
    @PostMapping("/campaign/comments")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignCommentsInsert insert){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.insertCampaignComments(insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 댓글 수정", tags = "사용자 - 체험단")
    @PutMapping("/campaign/comments")
    public ResponseEntity<?> campaign(@RequestHeader HttpHeaders header, @RequestBody CampaignCommentsUpdate update){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.updateCampaignComments(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 댓글 삭제", tags = "사용자 - 체험단")
    @DeleteMapping("/campaign/comments")
    public ResponseEntity<?> comments(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.deleteCampaignComments(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "찜 목록", tags = "사용자 - 체험단")
    @GetMapping("/favorites")
    public ResponseEntity<?> positive(@RequestHeader HttpHeaders header){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        json.put("list", campaignService.getFavoriteList(userSeq));
        json.put("count", campaignService.getFavoriteListCount(userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "찜 하기", tags = "사용자 - 체험단")
    @PostMapping("/favorites")
    public ResponseEntity<?> positives_insert(@RequestHeader HttpHeaders header, @RequestBody CampaignId target){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.insertFavorites(target.getId(), userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "찜 제거", tags = "사용자 - 체험단")
    @DeleteMapping("/favorites")
    public ResponseEntity<?> positives_delete(@RequestHeader HttpHeaders header, @RequestBody CampaignId target){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        campaignService.deleteFavorites(target.getId(), userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "알람", tags = "사용자 - 알람")
    @GetMapping("/alarm")
    public ResponseEntity<?> alarm(@RequestHeader HttpHeaders header){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        JSONObject json = new JSONObject();
        json.put("list", alarmService.getAlarmList(userSeq));
        json.put("count", alarmService.getAlarmListCount(userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
