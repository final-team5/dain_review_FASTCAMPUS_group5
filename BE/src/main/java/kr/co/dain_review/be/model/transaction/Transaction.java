package kr.co.dain_review.be.model.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    private Integer seq;
    private String id;
    private Integer userSeq;
    private String name;
    private Integer amount;
    private String method;
    private String status;
    private String currency;
    private String backName;
    private String cardType;
    private String receiptUrl;
    private String residentNumber;
    private String accountNumber;
    private Date transactionDate;
}
