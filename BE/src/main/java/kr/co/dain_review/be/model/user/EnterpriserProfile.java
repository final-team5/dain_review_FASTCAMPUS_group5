package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.dain_review.be.model.campaign.Campaign;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnterpriserProfile {
    private String id;
    private String profile;
    private String company;
    private Integer activeCampaignsCount;
    private Integer penalty;
    private Boolean editable;
    private ArrayList<Campaign> campaigns;

}
