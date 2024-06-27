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
    private MultipartFile profile;
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

}
