package kr.co.dain_review.be.model.main;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private String nickname;
    private String email;
    private String name;
    private String phone;
    private String bank;
    private String accountNumber;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private String subscriptionTitle;
    private boolean subscriptionStatus;
}
