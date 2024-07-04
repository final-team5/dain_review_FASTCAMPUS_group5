package kr.co.dain_review.be.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    private Integer type;
    private Integer amount;
    private String method;
    private Integer status;
    private String currency;
    private String bankName;
    private String cardType;
    private String receiptUrl;
    private String residentNumber;
    private String accountNumber;
}
