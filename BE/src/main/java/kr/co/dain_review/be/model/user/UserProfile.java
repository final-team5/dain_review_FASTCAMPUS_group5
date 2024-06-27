package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile {
//    seq
//    프로필 사진
//    닉네임
//    평점
//    등급
//    방문 수
//    패널티
//    블로그 일 방문자 수
//    인스타그램 팔로워 수
//    유튜브 구독자 수
//    틱톡 팔로워 수
//
//    진행중 체험단
//    진행 완료 체험단
    private Integer seq;
    private String profile;
    private Integer cancel;
    private Integer penalty;
    private Integer type;

}
