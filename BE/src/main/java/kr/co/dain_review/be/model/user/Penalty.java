package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Penalty {
    private Integer userSeq;
    private Integer cancel;
    private Integer penalty;
    private String reason = "";
}
