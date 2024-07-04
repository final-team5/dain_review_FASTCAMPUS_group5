package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerUpdate {
    private Integer userSeq;
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

    private byte[] profile;
    private String profileName;
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

    public InfluencerUpdate(AdminInfluencerUpdate update){
        this.userSeq = update.getSeq();
        this.email = update.getEmail();
        this.id = update.getId();
        this.pw = update.getPw();
        this.name = update.getName();
        this.phone = update.getPhone();
        this.nickname = update.getNickname();
        this.signupSource = update.getSignupSource();
        this.postalCode = update.getPostalCode();
        this.address = update.getAddress();
        this.addressDetail = update.getAddressDetail();
        this.birthdate = update.getBirthdate();
        this.gender = update.getGender();
        this.blogLink = update.getBlogLink();
        this.blogRank = update.getBlogRank();
        this.instagramLink = update.getInstagramLink();
        this.instagramRank = update.getInstagramRank();
        this.youtubeLink = update.getYoutubeLink();
        this.youtubeRank = update.getYoutubeRank();
        this.tiktokLink = update.getTiktokLink();
        this.tiktokRank = update.getTiktokRank();
        this.otherLink = update.getOtherLink();
        this.otherRank = update.getOtherRank();
        this.status = update.getStatus();

        try{
            this.profileName = update.getProfile().getOriginalFilename();
            this.profile = update.getProfile().getBytes();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public InfluencerUpdate(UserInfluencerUpdate update, Integer userSeq){
        this.userSeq = userSeq;
        this.email = update.getEmail();
        this.pw = update.getPw();
        this.name = update.getName();
        this.phone = update.getPhone();
        this.nickname = update.getNickname();
        this.postalCode = update.getPostalCode();
        this.address = update.getAddress();
        this.addressDetail = update.getAddressDetail();
        this.birthdate = update.getBirthdate();
        this.gender = update.getGender();
        this.blogLink = update.getBlogLink();
        this.instagramLink = update.getInstagramLink();
        this.youtubeLink = update.getYoutubeLink();
        this.tiktokLink = update.getTiktokLink();
        this.otherLink = update.getOtherLink();
        try {
            this.profile = update.getProfile().getBytes();
            this.profileName = update.getProfile().getOriginalFilename();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
