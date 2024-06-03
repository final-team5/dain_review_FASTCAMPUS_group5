package kr.co.dain_review.be.model.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Register {
    private String accessToken;
    private Integer authNum;
    private String pw;

    private String email;
    private String name;
    private String phone;
    private String signUpSource;
    private String company;
    private Integer type;
    private String role;

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


    public Boolean isBlogLink() {
        if(blogLink!=null){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isInstagramLink() {
        if(instagramLink!=null){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isYoutubeLink() {
        if(youtubeLink!=null){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isTiktokLink() {
        if(tiktokLink!=null){
            return true;
        } else {
            return false;
        }
    }

    public Boolean isOtherLink() {
        if(otherLink!=null){
            return true;
        } else {
            return false;
        }
    }
}
