package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdate {
    private String phone;
    private MultipartFile profile;
    private String birthdate;
    private Integer gender;
    private String address;
    private String addressDetail;
    private String blog;
    private String instagram;
    private String youtube;
    private String tiktok;
    private String other;
    private String postalCode;

    public Boolean isBlog(){
        if(blog!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isInstagram(){
        if(instagram!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isYoutube(){
        if(youtube!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isTiktok(){
        if(tiktok!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean isother(){
        if(other!=null){
            return true;
        }
        else {
            return false;
        }
    }
}
