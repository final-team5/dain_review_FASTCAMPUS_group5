package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminInfluencerUpdate {
    private Integer seq;
    private String email;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String nickname;
    private String signupSource;

    private Integer postalCode;
    private String address;
    private String addressDetail;

    private MultipartFile profile;
    private String birthdate;
    private Integer gender;

    private String blogLink;
    private String blogRank;
    private String instagramLink;
    private String instagramRank;
    private String youtubeLink;
    private String youtubeRank;
    private String tiktokLink;
    private String tiktokRank;
    private String otherLink;
    private String otherRank;

    private Integer status;
}
