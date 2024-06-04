package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPlatform {
    private Integer seq;
    private Integer userSeq;
    private String link;
    private String rank;
    private Integer visitors;
    private Integer follower;
    private Integer subscriberCount;
    private Integer videoCount;
    private Integer type;
}
