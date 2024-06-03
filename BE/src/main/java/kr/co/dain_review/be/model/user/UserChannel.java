package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserChannel {
    private Integer seq;
    private Integer userSeq;
    private String userIcon;
    private String userName;
    private String link;
    private String rank;
    private Integer visitors;
    private String channelTitle;
}
