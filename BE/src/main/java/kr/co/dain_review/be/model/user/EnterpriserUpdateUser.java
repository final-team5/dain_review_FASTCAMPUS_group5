package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriserUpdateUser {
    private String email;
    private String pw;
    private String name;
    private String phone;
    private String company;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private MultipartFile profile;
    private String businessNumber;
    private String representative;
    private MultipartFile attachment;
}
