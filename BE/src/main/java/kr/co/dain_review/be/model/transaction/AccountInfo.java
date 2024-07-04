package kr.co.dain_review.be.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfo {
    private String bank;
    private String name;
    private String accountNumber;
    private String residentNumberFront;
    private String residentNumberBack;
    private Integer amount;
}
