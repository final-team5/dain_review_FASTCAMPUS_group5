package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesSocialSignup {
    private String accessToken;
    private Integer loginType;
    private String name;
    private String phone;
    private String company;
    private String signupSource;
    private String postalCode;
    private String address;
    private String addressDetail;
    private MultipartFile profile;
}
