package kr.co.dain_review.be.model.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Post {
    private Integer seq;
    private Integer userSeq;
    private String nickname;
    private String category;
    private String title;
    private String contents;
    private Date createDate;
    private Date createTime;
    private Integer viewCount;
    private String userIcon;
    private Integer userType;

    public String getCreateDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd(E)", Locale.KOREA);
        String strNowDate = simpleDateFormat.format(createDate);
        return strNowDate;
    }

    public String getCreateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strNowDate = simpleDateFormat.format(createDate);
        return strNowDate;
    }

    public String getUserType(){
        switch (userType){
            case 1 :
                return "관리자";
            case 2 :
                return "광고주";
            case 3 :
                return "인플루언서";
        }
        return null;
    }

}
