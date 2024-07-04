package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.dain_review.be.model.campaign.Campaign;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesProfile {
    private String id;
    private Integer type;
    private String profile;
    private String company;
    private Integer activeCampaignsCount;
    private Integer penalty;
    private Boolean editable;
    private String agencyStatus;
    private ArrayList<Campaign> campaigns;

}
