package kr.co.dain_review.be.model.campaign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MissionInsert {
    private String title;
    private Boolean hashTag;
    private Boolean map;
    private Boolean link;
    private Integer characters;
    private Integer videoLength;
    private Integer pictureCount;
    private Boolean accountTag;
    private Boolean sound;
    private Boolean advertisingDisplay;
}
