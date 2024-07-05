package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cancel {
    private Integer seq;
    private String type;
    private Integer userSeq;
    private Integer campaignSeq;
    private String reason;
    private String evidentiary1;
    private String evidentiary2;
    private String evidentiary3;

}
