package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetail {
    private Integer seq;
    private String id;
    private String name;
    private String phone;
    private Date createDate;
    private String signupSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private Integer point;
    private Integer status;
    private Integer cancel;
    private Integer penalty;
}
