package com.example.finalproject.domain.campaign.dto.request;


import com.example.finalproject.domain.campaign.dto.City;
import com.example.finalproject.domain.campaign.dto.District;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
	private City city;
	private District district;
}
