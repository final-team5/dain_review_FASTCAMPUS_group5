package kr.co.dain_review.be.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfluencer {
//    진행 전 보여줘야하는 항목
//    계정URL
//    이름
//    선정 여부
//    블로그 지수
//    일 방문
//    평점
//    취소횟수
//    신청메시지
//    관심
//    블랙

//    진행 후 보여줘야하는 항목
//    +연락처
//    +주소
//    +리뷰등록일
//    +순위체크
//    +링크주소
//    +첨부자료

    private Integer seq;
    private Integer channelUrl;
    private String name;
    private Boolean application; //선정 여부
    private Integer rank;
    private Integer visitors;
    private Double rating;
    private Integer cancel;
    private String message;
    private Integer opinions;

    private String phone;
    private String address;
    private String reviewDate;
    private String rankingCheck;
    private String reviewLink;
    private String attachments;

}
