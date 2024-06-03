package kr.co.dain_review.be.model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayappFeedback {
//    private String memo;
    private String userSeq;
    private String subscriptionSeq;
    private String linkkey;
    private String linkval;
//    private String price;
    private String payState;
    private String cardNum;
    private String cardName;
    private String rebillNo;
    private String mulNo;
    private String payDate;
    private String payType;


    public PayappFeedback(Map<String, String[]> map){

        this.userSeq = map.get("var1")[0];
        this.subscriptionSeq = map.get("var2")[0];
        this.linkkey = map.get("linkkey")[0];
        this.linkval = map.get("linkval")[0];
        this.payState = map.get("pay_state")[0];
        this.cardNum = map.get("card_num")[0];
        this.cardName = map.get("card_name")[0];
        this.rebillNo = map.get("rebill_no")[0];
        this.mulNo = map.get("mul_no")[0];
        this.payDate = map.get("pay_date")[0];
        this.payType = map.get("pay_type")[0];


//        this.memo = map.get("memo")[0];
//        this.reqaddr = map.get("reqaddr")[0];
//        this.reqdate = map.get("reqdate")[0];
//        this.payMemo = map.get("pay_memo")[0];
//        this.pay_addr = map.get("pay_addr")[0];
//        this.paymethod_group = map.get("paymethod_group")[0];
//        this.payurl = map.get("payurl")[0];
//        this.csturl = map.get("csturl")[0];
//        this.vccode = map.get("vccode")[0];
//        this.donate_name = map.get("donate_name")[0];
//        this.donate_identity = map.get("donate_identity")[0];
//        this.payauthcode = map.get("payauthcode")[0];
//        this.cardinst = map.get("cardinst")[0];
//        this.card_quota = map.get("card_quota")[0];
//        this.noinf = map.get("noinf")[0];
//        this.currency = map.get("currency")[0];
//        this.buyerid = map.get("buyerid")[0];
//        this.amount_taxable = map.get("amount_taxable")[0];
//        this.amount_taxfree = map.get("amount_taxfree")[0];
//        this.amount_vat = map.get("amount_vat")[0];
//        this.feedbacktype = map.get("feedbacktype")[0];
    }

    public Integer getPayState() {
        return Integer.parseInt(payState);
    }

//    public Integer getPrice() {
//        return Integer.parseInt(price);
//    }
}
