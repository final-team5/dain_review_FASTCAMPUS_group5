package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Influencer {
    private Integer seq;
    private String id;
    private String name;
    private String phone;

    private String blogRank;
    private String instagramRank;
    private String youtubeRank;
    private String tiktokRank;
    private String otherRank;

    private Date createDate;

    public String getCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strNowDate = simpleDateFormat.format(createDate);
        return strNowDate;
    }

}
