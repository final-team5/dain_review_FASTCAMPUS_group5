package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneId;
import java.util.Date;

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

	public static UserInfo of(Integer seq, String email, String id, String pw, String role, String name, String phone,
							  Date createDate, String signupSource, Integer postalCode, String address, String addressDetail,
							  Integer point, Integer status, Integer cancel, Integer penalty, Integer type) {
		return new UserInfo(seq, email, id, pw, role, name, phone, createDate, signupSource, postalCode, address, addressDetail, point, status, cancel, penalty, type);
	}

	public static UserInfo from(User user) {
		return UserInfo.of(
				user.getSeq(),
				user.getEmail(),
				user.getId(),
				user.getPw(),
				user.getRole(),
				user.getName(),
				user.getPhone(),
				Date.from(user.getCreateDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
				user.getSignupSource(),
				user.getPostalCode(),
				user.getAddress(),
				user.getAddressDetail(),
				user.getPoint(),
				user.getStatus(),
				user.getCancel(),
				user.getPenalty(),
				user.getType()
		);
	}
}
