package com.example.finalproject.domain.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "대행사 신청에 필요한 요청 정보")
public class AgencyInsertRequest {

    @ApiModelProperty(value = "회사명", required = true, example = "하이파이브 앰버서더 호텔")
    private String company;

    @ApiModelProperty(value = "사업자번호", required = true, example = "123-45-67890")
    private String businessNumber;

    @ApiModelProperty(value = "대표자명", required = true, example = "김대표")
    private String representative;

    @ApiModelProperty(value = "첨부파일 경로 또는 이름", required = false, example = "example.pdf")
    private String attachment;

    @ApiModelProperty(value = "우편번호", required = true, example = "45609")
    private String postalCode;

    @ApiModelProperty(value = "주소", required = true, example = "부산 사하구 낙동남로 2043")
    private String address;

    @ApiModelProperty(value = "상세주소", required = true, example = "")
    private String addressDetail;
}