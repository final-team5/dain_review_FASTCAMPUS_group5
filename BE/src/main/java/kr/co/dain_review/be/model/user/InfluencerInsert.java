package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerInsert {
    private String id;
    private String pw;
    private String name;
    private Integer type;
    private String address;
    private String detailedAddress;
    private String phone;
    private byte[] profile;
    private String fileName;
    private Integer point;
    private String nickname;
    private Integer gender;
    private Date birthdate;

    private String blogLink;
    private String instagramLink;
    private String youtubeLink;
    private String tiktokLink;
    private String otherLink;
    private String blogRank;
    private String instagramRank;
    private String youtubeRank;
    private String tiktokRank;
    private String otherRank;

    public InfluencerInsert(AdminInfluencerInsert insert){
        this.id = insert.getId();
        this.pw = insert.getPw();
        this.name = insert.getName();
        this.type = insert.getType();
        this.address = insert.getAddress();
        this.detailedAddress = insert.getDetailedAddress();
        this.phone = insert.getPhone();
        this.point = insert.getPoint();
        this.nickname = insert.getNickname();
        this.gender = insert.getGender();
        this.birthdate = insert.getBirthdate();
        this.blogLink = insert.getBlogLink();
        this.instagramLink = insert.getInstagramLink();
        this.youtubeLink = insert.getYoutubeLink();
        this.tiktokLink = insert.getTiktokLink();
        this.otherLink = insert.getOtherLink();
        this.blogRank = insert.getBlogRank();
        this.instagramRank = insert.getInstagramRank();
        this.youtubeRank = insert.getYoutubeRank();
        this.tiktokRank = insert.getTiktokRank();
        this.otherRank = insert.getOtherRank();
        try {
            this.profile = insert.getProfile().getBytes();
            this.fileName = insert.getProfile().getOriginalFilename();
        } catch (Exception e){
            System.out.println(e);
        }

    }

}
