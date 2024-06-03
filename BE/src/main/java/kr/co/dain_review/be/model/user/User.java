package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer seq;
    private String id;
    private String pw;
    private Integer type;
    private String address;
    private String phone;
    private Integer point;
    private String name;
    private Integer cancel;
    private Integer gender;
    private Date birthdate;
    private Integer rank;
    private Date createDate;
    private ArrayList<UserChannel> userChannels;
    private ArrayList<UserPreference> userPreferences;
}
