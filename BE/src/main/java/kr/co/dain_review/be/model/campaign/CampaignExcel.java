package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignExcel {
    private String date;
    private String nickname;
    private String pcViews;
    private String mobileViews;

    public Integer getPcViews() {
        return Integer.parseInt(pcViews);
    }

    public Integer getMobileViews() {
        return Integer.parseInt(mobileViews);
    }
}
