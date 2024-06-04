package kr.co.dain_review.be.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductSearch {
    private String searchWord = "";
    private String city;
    private ArrayList<String> district;
    private Integer typeSeq;
    private Integer categorySeq;
    private Integer channelSeq;
    private Integer status;
    private String orderParam;
    private boolean isPremium;
    private ArrayList<Location> location;

    private int page = 1;

    public int getPage() {
        return (page - 1) * 10;
    }

    public String getSearchWord() {
        return "%"+searchWord+"%";
    }
}
