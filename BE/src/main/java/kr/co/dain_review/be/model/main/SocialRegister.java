package kr.co.dain_review.be.model.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialRegister {
    private String accessToken;
    private String name;
    private String nickname;
    private String phone;
    private String placeName;
    private String placeAddress;
}
