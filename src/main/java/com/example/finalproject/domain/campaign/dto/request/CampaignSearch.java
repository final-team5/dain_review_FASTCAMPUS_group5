package com.example.finalproject.domain.campaign.dto.request;

import com.example.finalproject.domain.campaign.dto.Category;
import com.example.finalproject.domain.campaign.dto.Location;
import com.example.finalproject.domain.campaign.dto.Sns;
import com.example.finalproject.domain.campaign.dto.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignSearch {

	private String searchWord = "";
	private Integer city;
	private ArrayList<String> districts;
	private Integer typeSeq;
	private Integer categorySeq;
	private Integer platformSeq;
	private Integer status;
	private String orderParam;
	private boolean isPremium;
	private ArrayList<Location> location;

	private int page = 1;

	public int getPage() {
		return (page - 1) * 10;
	}

	public String getSearchWord() {
		return "%" + searchWord + "%";
	}

	public Category getCategory() {
		return Category.fromValue(categorySeq);
	}

	public Type getType() {
		return Type.fromValue(typeSeq);
	}

	public Sns getSns() { return Sns.fromValue(platformSeq); }
}
