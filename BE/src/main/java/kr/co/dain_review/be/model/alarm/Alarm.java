package kr.co.dain_review.be.model.alarm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alarm {
    private Integer seq;
    private Integer userSeq;
    private Integer targetSeq;
    private Integer targetType;
    private String message;
}
