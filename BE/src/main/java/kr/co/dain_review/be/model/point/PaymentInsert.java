package kr.co.dain_review.be.model.point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentInsert {
    private Integer userSeq;
    private Integer orderId;
    private Integer amount;
    private String paymentMethod;
    private String paymentStatus;
    private String currency;
    private String bankName;
    private String cardType;
    private String paymentGateway;
    private String receiptUrl;
    private String refundStatus;
}
