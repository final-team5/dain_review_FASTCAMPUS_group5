package com.example.finalproject.domain.user.dto;

import com.example.finalproject.domain.user.entity.Businesses;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBusinessesDto {

    private Integer seq;

    private UserDto userDto;

    private String company;

    private String businessNumber;

    private String representative;



    public static UserBusinessesDto of(Integer seq, UserDto userDto, String company, String businessNumber, String representative) {
        return new UserBusinessesDto(seq, userDto, company, businessNumber, representative);
    }

    public static UserBusinessesDto from(Businesses businesses) {
        return UserBusinessesDto.of(
                businesses.getSeq(),
                UserDto.from(businesses.getUser()),
                businesses.getCompany(),
                businesses.getBusinessNumber(),
                businesses.getRepresentative()
        );
    }

}
