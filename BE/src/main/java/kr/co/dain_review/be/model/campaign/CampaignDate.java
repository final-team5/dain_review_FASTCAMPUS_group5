package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignDate {
    private Date experienceStartDate;
    private Date experienceEndDate;

    public String getExperienceStartDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String strNowDate = simpleDateFormat.format(experienceStartDate);
        return strNowDate;
    }
    public String getExperienceEndDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String strNowDate = simpleDateFormat.format(experienceEndDate);
        return strNowDate;
    }
}
