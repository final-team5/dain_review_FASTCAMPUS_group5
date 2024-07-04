package kr.co.dain_review.be.model.community;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityCommentsUpdate {
    private Integer seq;
    private Integer communitySeq;
    private Integer commentSeq;
    private String comment;
}
