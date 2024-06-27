package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgencyDetail {
    private String id;
    private String name;
    private String phone;
    private String company;
    private String address;
    private String businessNumber;
    private String representativeName;
    private String attachedFile;
    private Integer status;
    private String reason;

}
