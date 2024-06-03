package kr.co.dain_review.be.model.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminReportInsert {
    private Integer productSeq;
    private Integer userSeq;
    private ArrayList<ReportContent> reportContents;
    private ArrayList<ReportKeyword> reportKeywords;
}
