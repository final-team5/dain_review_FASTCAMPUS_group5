package kr.co.dain_review.be.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.main.*;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.campaign.CampaignSearch;
import kr.co.dain_review.be.model.user.User;
import kr.co.dain_review.be.service.InstagramService;
import kr.co.dain_review.be.service.PostService;
import kr.co.dain_review.be.service.CampaignService;
import kr.co.dain_review.be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

@Api(tags = "공용")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class PublicController {
    private final CampaignService campaignService;
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

    private final InstagramService instagramService;

    @ApiOperation(value = "프로필 보기", tags = "사용자 - 프로필")
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> profile(@RequestHeader HttpHeaders header, @PathVariable String id){
        Integer userSeq = null;
        if(header.getFirst("Authorization")!=null) {
            String token = header.getFirst("Authorization");
            userSeq = tokenProvider.getSeq(token);
        }
        return new ResponseEntity<>(userService.getProfile(userSeq, id), HttpStatus.OK);
    }


    @ApiOperation(value = "로그인", tags = "공개 - 회원")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) throws ParseException {

            User user = userService.getUser(login.getEmail(), 1);
            JSONObject jo = new JSONObject();
            if (user == null) {
                jo.put("message", "사용자를 찾을 수 없습니다");
                return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
            }
            if (!passwordEncoder.matches(login.getPw(), user.getPw())) {
                jo.put("message", "비밀번호가 일치하지 않습니다");
                return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
            }
            jo = setReturnValue(user);
            jo.put("message", "로그인 되었습니다.");
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

    }

    @ApiOperation(value = "닉네임 중복 체크", tags = "공개 - 회원")
    @PostMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(@RequestBody Nickname nickname){
        JSONObject jo = new JSONObject();

            if (userService.checkNickname(nickname.getNickname())) {
                jo.put("message", "이미 사용중인 닉네임 입니다");
                return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
            }
            jo.put("message", "사용 가능한 닉네임 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

    }

    //회원 가입
    @ApiOperation(value = "회원 가입", tags = "공개 - 회원")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LocalRegister localRegister){
        ObjectMapper objectMapper = new ObjectMapper();
        Register register = objectMapper.convertValue(localRegister, Register.class);
        register.setType(1);
        if (localRegister.getEntrepreneur()){
            register.setRole("ROLE_ENTERPRISER");
        }
        if (localRegister.getInfluencer()){
            register.setRole("ROLE_INFLUENCER");
        }

        JSONObject jo = new JSONObject();
        User user = userService.getUser(register.getEmail(), 1);
        if (user!=null) {
            jo.put("message", "이미 가입된 아이디가 있습니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkEmail(register.getEmail())){
            jo.put("message", "이미 사용중인 이메일 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkNickname(register.getNickname())){
            jo.put("message", "이미 사용중인 닉네임 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkPhone(register.getPhone())){
            jo.put("message", "이미 사용중인 전화번호 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        String pw = passwordEncoder.encode(register.getPw());
        register.setPw(pw);
        userService.register(register);
//            kakao.sendMessageRegister(register.getPhone());
        jo.put("message", "회원가입 되었습니다");
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);

    }

    //소셜 로그인
    @ApiOperation(value = "소셜 로그인", tags = "공개 - 회원")
    @PostMapping("/social-login")
    public ResponseEntity<?> SocialRegister(@RequestBody SocialLogin socialLogin) throws ParseException {

        JSONObject json = getUser(socialLogin.getAccessToken());
        JSONObject kakaoAccount = (JSONObject) json.get("kakao_account");
        String email = kakaoAccount.get("email").toString();
        String pw = json.get("id").toString();
        JSONObject jo = new JSONObject();
        User user = userService.getUser(email, 2);

        if(user==null){
            jo.put("message", "사용자를 찾을 수 없습니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
        }
        if (!passwordEncoder.matches(pw, user.getPw())) {
            jo.put("message", "잘못된 토큰 입니다.");
            return new ResponseEntity<>(jo.toString(), HttpStatus.BAD_REQUEST);
        }

        jo = setReturnValue(user);
        jo.put("message", "로그인 되었습니다.");
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);


    }

    //소셜 회원가입
    @ApiOperation(value = "소셜 회원 가입", tags = "공개 - 회원")
    @PostMapping("/social-register")
    public ResponseEntity<?> SocialRegister(@RequestBody SocialRegister socialRegister){

        JSONObject json = getUser(socialRegister.getAccessToken());
        JSONObject kakaoAccount = (JSONObject) json.get("kakao_account");
        String email = kakaoAccount.get("email").toString();
        String pw = json.get("id").toString();

        ObjectMapper objectMapper = new ObjectMapper();
        Register register = objectMapper.convertValue(socialRegister, Register.class);
        register.setEmail(email);
        register.setPw(passwordEncoder.encode(pw));
        register.setType(2);
        if (socialRegister.getEntrepreneur()){
            register.setRole("ROLE_ENTERPRISER");
        }
        if (socialRegister.getInfluencer()){
            register.setRole("ROLE_INFLUENCER");
        }

        JSONObject jo = new JSONObject();
        User user = userService.getUser(register.getEmail(), 2);
        if (user!=null) {
            jo.put("message", "이미 가입된 아이디가 있습니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkEmail(register.getEmail())){
            jo.put("message", "이미 사용중인 이메일 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkNickname(register.getNickname())){
            jo.put("message", "이미 사용중인 닉네임 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        if(userService.checkPhone(register.getPhone())){
            jo.put("message", "이미 사용중인 전화번호 입니다");
            return new ResponseEntity<>(jo.toString(), HttpStatus.CONFLICT);
        }
        userService.register(register);
        jo.put("message", "회원가입 되었습니다");
        return new ResponseEntity<>(jo.toString(), HttpStatus.OK);
    }

    //아이디 찾기
    @ApiOperation(value = "아이디 찾기", tags = "공개 - 회원")
    @PostMapping("/find-email")
    public ResponseEntity<?> findUserEmail(@RequestBody FindEmail findEmail){

            JSONObject json = new JSONObject();
            if(userService.findEmailCheck(findEmail)){
                json.put("message", "일치하는 아이디가 없습니다");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            json.put("email", userService.findEmail(findEmail));
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }

    //이메일 인증
    @ApiOperation(value = "이메일 인증(회원가입)", tags = "공개 - 회원")
    @PostMapping("/email-verification")
    public ResponseEntity<?> verification(@RequestBody Email email){

            JSONObject json = new JSONObject();
            if(userService.checkEmail(email.getEmail())){
                json.put("message", "이미 사용중인 이메일 입니다");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            userService.emailVerification(email);
            json.put("message", "SUCCESS");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }


    @ApiOperation(value = "회원 인증(비밀번호 찾기)", tags = "공개 - 회원")
    @PostMapping("/user-verification")
    public ResponseEntity<?> userverification(@RequestBody Email email){

            JSONObject json = new JSONObject();
            if(!userService.checkEmail(email.getEmail())){
                json.put("message", "가입한 이메일이 없습니다.");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            if(userService.checkSocialUser(email.getEmail())){
                json.put("message", "소셜 가입 회원입니다.");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            userService.emailVerification(email);
            json.put("message", "SUCCESS");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }

    @ApiOperation(value = "회원 인증 확인", tags = "공개 - 회원")
    @PostMapping("/user-verification-check")
    public ResponseEntity<?> userVerificationCheck(@RequestBody UserVerification userVerification){

            JSONObject json = new JSONObject();

            if(userService.checkSocialUser(userVerification.getEmail())){
                json.put("message", "소셜 가입 회원입니다.");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }

            Integer authenticationNumber = userService.getAuthenticationNumber(userVerification);
            if(!userVerification.getAuth().equals(authenticationNumber)){
                json.put("message", "인증번호를 확인해주세요");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            userService.emailCertification(userVerification);

            if(userService.getFindUser(userVerification)){
                json.put("message", "일치하는 정보를 가진 회원이 없습니다");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }

            userService.userCertification(userVerification);
            json.put("message", "인증이 완료되었습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }

    @ApiOperation(value = "비밀번호 찾기", tags = "공개 - 회원")
    @PostMapping("/find-password")
    public ResponseEntity<?> findPassword(@RequestBody FindPassword findPassword){

            JSONObject json = new JSONObject();

            if(!userService.findPasswordVerification(findPassword)){
                json.put("message", "비밀번호 변경에 실패했습니다. 회원 인증 또는 이메일 인증을 진행해주세요");
                return new ResponseEntity<>(json.toString(), HttpStatus.BAD_REQUEST);
            }
            String encode = passwordEncoder.encode(findPassword.getNewPw());
            findPassword.setNewPw(encode);
            userService.setNewPassword(findPassword);
            json.put("message", "비밀번호 변경이 완료되었습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);

    }

    private JSONObject getUser (String accessToken){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> response = null;
            response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonObject = objectMapper.convertValue(responseBody, JSONObject.class);
            return jsonObject;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    public JSONObject setReturnValue(User user) throws ParseException {
        String new_token = tokenProvider.createToken(user);
        JSONObject jo = new JSONObject();
        jo.put("token", new_token);
        jo.put("name", user.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNowDate = simpleDateFormat.format(tokenProvider.getExpireDate(new_token));
        jo.put("expireDate", strNowDate);
        return jo;
    }


    //체험단
    @ApiOperation(value = "체험단 리스트", tags = "공개 - 체험단")
    @GetMapping("/campaign")
    public ResponseEntity<?> campaign(CampaignSearch search){
        JSONObject json = new JSONObject();
        json.put("list", campaignService.getList(search));
        json.put("totalCount", campaignService.getListCount(search));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    //체험단 상세
    @ApiOperation(value = "체험단 상세", tags = "공개 - 체험단")
    @GetMapping("/campaign/{seq}")
    public ResponseEntity<?> campaign(@PathVariable Integer seq){
        return new ResponseEntity<>(campaignService.getDetail(seq), HttpStatus.OK);
    }


    @ApiOperation(value = "공지 리스트", tags = "공개 - 공지")
    @GetMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, Search search){
        JSONObject json = new JSONObject();
        json.put("list", postService.select(search, 1));
        json.put("totalCount", postService.selectCount(search, 1));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "공지 상세", tags = "공개 - 공지")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", postService.selectDetail(seq, 1));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @GetMapping("/getAccessToken")
    public String getAccessToken(@RequestParam String code) {
        String shortLivedToken = instagramService.getAccessToken(code);
        String longLivedAccessToken = instagramService.getLongLivedAccessToken(shortLivedToken);
        return longLivedAccessToken;
    }

    @GetMapping("/reels/comments/count")
    public Mono<ResponseEntity<Integer>> getReelsCommentsCount(@RequestParam String reelsId, @RequestParam String accessToken) {
        return instagramService.getReelsCommentsCount(reelsId, accessToken)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(0)));
    }
}
