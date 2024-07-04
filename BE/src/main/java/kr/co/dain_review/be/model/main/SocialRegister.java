package kr.co.dain_review.be.model.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialRegister {
    private Boolean influencer;
    private Boolean Entrepreneur;

    private String accessToken;
    private String name;
    private String phone;
    private String signupSource;
    private String company;

    private String blogLink;
    private String instagramLink;
    private String youtubeLink;
    private String tiktokLink;
    private String otherLink;

    private String postalCode;
    private String address;
    private String addressDetail;

    private String nickname;

    private MultipartFile profile;
    private String birthdate;
    private Integer gender;

}
