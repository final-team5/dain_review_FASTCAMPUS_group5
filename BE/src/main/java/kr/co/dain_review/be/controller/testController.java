package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import kr.co.dain_review.be.util.Tiktok;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "회원")
@RequestMapping("/test")
@RequiredArgsConstructor
@RestController
public class testController {

    @GetMapping("/test")
    public ResponseEntity<?> SocialRegister(String url) {
        JSONObject json = new JSONObject();
        json.put("result", Tiktok.getFollowerCount(url));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
