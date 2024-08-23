package com.example.finalproject.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserInfo {
	private Integer seq;
	private String email;
	private String id;
	private String pw;
	private String role;
	private String name;
	private String phone;
	private Date createDate;
	private String signupSource;
	private Integer postalCode;
	private String address;
	private String addressDetail;
	private Integer point;
	private Integer status;
	private Integer cancel;
	private Integer penalty;
	private Integer type;
}
