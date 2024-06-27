package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerDetail {
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String signUpSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private String profile;
    private Integer point;
    private Integer status;
    private Integer cancel;
    private Integer penalty;



    private String nickname;
    private Date birthdate;
    private Integer gender;
    private String blogLink;
    private String blogRank;
    private Integer blogVisitors;
    private String instagramLink;
    private String instagramRank;
    private Integer instagramFollower;
    private String youtubeLink;
    private String youtubeRank;
    private Integer youtubeSubscriber;
    private String tiktokLink;
    private String tiktokRank;
    private Integer tiktokFollower;
    private String otherLink;
    private String otherRank;
}
