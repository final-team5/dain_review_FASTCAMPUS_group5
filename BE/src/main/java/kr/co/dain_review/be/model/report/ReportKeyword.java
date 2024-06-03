package kr.co.dain_review.be.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportKeyword {
    private Integer seq;
    private Integer reportSeq;
    private String keyword;
    private Integer visitors;
}
