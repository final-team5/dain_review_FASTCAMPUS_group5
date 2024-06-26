package kr.co.dain_review.be.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.dain_review.be.model.campaign.Campaign;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InfluencerProfile {
    private String id;
    private String profile;
    private String nickname;
    private Integer applicationCampaign;
    private Integer experienceCampaign;
    private Integer cancel;
    private Integer penalty;

    private String blogLink;
    private String blogRank;
    private String instagramLink;
    private String instagramRank;
    private String youtubeLink;
    private String youtubeRank;
    private String tiktokLink;
    private String tiktokRank;
    private String otherLink;
    private String otherRank;

    private ArrayList<Campaign> campaigns;


}
