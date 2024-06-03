package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminUserInsert {
    private String id;
    private String pw;
    private String name;
    private Integer type;
    private String address;
    private String detailedAddress;
    private String phone;
    private Integer point;
    private String nickname;
    private Integer gender;
    private Date birthdate;
    private Integer rankSeq;

    private String blogLink;
    private String instagramLink;
    private String youtubeLink;
    private String tiktokLink;
    private String otherLink;

}
