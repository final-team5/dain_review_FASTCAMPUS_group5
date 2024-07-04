package kr.co.dain_review.be.model.community;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityComment {
    private Integer seq;
    private Integer userSeq;
    private Integer commentSeq;
    private String comment;
    private Date createDate;

    public String getCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strNowDate = simpleDateFormat.format(createDate);
        return strNowDate;
    }
}
