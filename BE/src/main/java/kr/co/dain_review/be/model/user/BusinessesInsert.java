package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesInsert {
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
    private Integer point;
    private Integer penalty;

    public BusinessesInsert(AdminBusinessesInsert insert){
        this.type = insert.getType();
        this.id = insert.getId();
        this.pw = insert.getPw();
        this.name = insert.getName();
        this.phone = insert.getPhone();
        this.company = insert.getCompany();
        this.signupSource = insert.getSignupSource();
        this.postalCode = insert.getPostalCode();
        this.address = insert.getAddress();
        this.addressDetail = insert.getAddressDetail();
        this.businessNumber = insert.getBusinessNumber();
        this.representative = insert.getRepresentative();
        this.status = insert.getStatus();
        this.point = insert.getPoint();
        this.penalty = insert.getPenalty();
        try {
            this.profile = insert.getProfile().getBytes();
            this.profileName = insert.getProfile().getOriginalFilename();
            this.attachment = insert.getAttachment().getBytes();
            this.attachmentName = insert.getAttachment().getOriginalFilename();
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
