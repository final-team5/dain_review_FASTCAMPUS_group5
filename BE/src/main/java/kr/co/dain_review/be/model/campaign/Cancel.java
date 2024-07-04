package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cancel {
    private String type;
    private Integer userSeq;
    private String campaignId;
    private String reason;
    private MultipartFile evidentiary1;
    private MultipartFile evidentiary2;
    private MultipartFile evidentiary3;

}
