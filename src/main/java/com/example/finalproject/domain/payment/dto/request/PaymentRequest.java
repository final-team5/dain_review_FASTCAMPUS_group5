package com.example.finalproject.domain.payment.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentRequest {

    private String accountNumber;
    private int amount;
    private String bankName;
    private String cardType;
    private String currency;
    private String method;
    private String receiptUrl;
    private String residentNumber;
    private int status;
    private int type;
}