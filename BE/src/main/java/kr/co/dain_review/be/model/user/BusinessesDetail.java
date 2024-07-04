package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesDetail {
    private Integer seq;
    private Integer type;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String company;
    private String signupSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private String profile;
    private String businessNumber;
    private String representative;
    private String attachment;
    private Integer status;
    private Integer point;
    private Integer penalty;
}
