package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Integer seq;

    private String email;

    private String id;

    private String pw;

    private String role;

    private String name;

    private String phone;

    private String profile;

    private Date createDate;

    private String signUpSource;

    private String postalCode;

    private String address;

    private String addressDetail;

    private Integer point;

    private Integer status;

    private Integer cancel;

    private Integer penalty;

    private Integer type;

    public static UserDto of(Integer seq, String email, String id, String pw, String role, String name, String phone, String profile, Date createDate, String signUpSource, String postalCode, String address, String addressDetail, Integer point, Integer status, Integer cancel, Integer penalty, Integer type) {
        return new UserDto(seq, email, id, pw, role, name, phone, profile, createDate, signUpSource, postalCode, address, addressDetail, point, status, cancel, penalty, type);
    }

    public static UserDto from(User user) {
        return UserDto.of(
                user.getSeq(),
                user.getEmail(),
                user.getId(),
                user.getPw(),
                user.getRole(),
                user.getName(),
                user.getPhone(),
                user.getProfile(), user.getCreateDate(),
                user.getSignupSource(),
                user.getPostalCode().toString(),
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
