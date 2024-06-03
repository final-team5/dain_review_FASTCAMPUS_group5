package kr.co.dain_review.be.model.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Search {
    private String searchWord = "";
    private String searchType;
    private int page = 1;

    public int getPage() {
        return (page - 1) * 10;
    }

    public String getSearchWord() {
        return "%"+searchWord+"%";
    }
}
