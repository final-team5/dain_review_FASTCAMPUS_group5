package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriserInsert {
    private Integer type;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String company;
    private String signUpSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private MultipartFile profile;
    private String businessNumber;
    private String representative;
    private MultipartFile attachment;
    private Integer status;
    private Integer point;
    private Integer penalty;
}
