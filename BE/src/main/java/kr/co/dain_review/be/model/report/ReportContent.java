package kr.co.dain_review.be.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportContent {
    private Integer seq;
    private Integer reportSeq;
    private Integer userSeq;
    private String title;
    private String url;
    private Integer like;
    private Integer comments;
}
