package com.example.finalproject.domain.user.dto.request;

import com.example.finalproject.domain.user.entity.enums.SignUpSourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessesSignup {
	private String email;
	private String pw;
	private String name;
	private String phone;
	private String company;
//	private String signupSource;
	private SignUpSourceType signupSource;
	private String postalCode;
	private String address;
	private String addressDetail;
//	private MultipartFile profile;
}
