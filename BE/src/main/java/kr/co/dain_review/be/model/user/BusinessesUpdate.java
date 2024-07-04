package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesUpdate {
    private Integer seq;
    private Integer type;
    private String id;
    private String pw;
    private String name;
    private String phone;
    private String company;
    private String signupSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private byte[] profile;
    private String profileName;
    private String businessNumber;
    private String representative;
    private byte[] attachment;
    private String attachmentName;
    private Integer status;

    public BusinessesUpdate(AdminBusinessesUpdate update){
        this.seq = update.getSeq();
        this.type = update.getType();
        this.id = update.getId();
        this.pw = update.getPw();
        this.name = update.getName();
        this.phone = update.getPhone();
        this.company = update.getCompany();
        this.signupSource = update.getSignupSource();
        this.postalCode = update.getPostalCode();
        this.address = update.getAddress();
        this.addressDetail = update.getAddressDetail();
        this.businessNumber = update.getBusinessNumber();
        this.representative = update.getRepresentative();
        this.status = update.getStatus();
        try {
            this.profile = update.getProfile().getBytes();
            this.profileName = update.getProfile().getOriginalFilename();
            this.attachment = update.getAttachment().getBytes();
            this.attachmentName = update.getAttachment().getOriginalFilename();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public BusinessesUpdate(UserBusinessesUpdate update, Integer userSeq){
        this.seq = userSeq;
        this.pw = update.getPw();
        this.name = update.getName();
        this.phone = update.getPhone();
        this.company = update.getCompany();
        this.postalCode = update.getPostalCode();
        this.address = update.getAddress();
        this.addressDetail = update.getAddressDetail();
        this.businessNumber = update.getBusinessNumber();
        this.representative = update.getRepresentative();
        try {
            this.profile = update.getProfile().getBytes();
            this.profileName = update.getProfile().getOriginalFilename();
            this.attachment = update.getAttachment().getBytes();
            this.attachmentName = update.getAttachment().getOriginalFilename();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
