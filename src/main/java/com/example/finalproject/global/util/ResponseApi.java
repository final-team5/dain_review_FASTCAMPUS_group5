package com.example.finalproject.global.util;

import com.example.finalproject.global.exception.type.CustomException;
import com.example.finalproject.global.util.Response.Error;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseApi<T> {
    private int resultCode;
    private String resultMessage;
    private T data;

    public static <T> ResponseApi<T> success(HttpStatus status, T data) {
        return ResponseApi.<T>builder()
                .resultCode(status.value())
                .resultMessage(status.getReasonPhrase())
                .data(data)
                .build();
    }

    public static ResponseApi<?> failed(CustomException ex) {
        return ResponseApi.<Error>builder()
            .resultCode(ex.getStatusCode().value())
            .resultMessage(ex.getStatusCode().getReasonPhrase())
            .data(new Error(Collections.singletonList(ex.getInfo())))
            .build();
    }
    
    public static <T> ResponseApi<T> failed(CustomException ex, T data) {
        return ResponseApi.<T>builder()
                .resultCode(ex.getStatusCode().value())
                .resultMessage(ex.getStatusCode().getReasonPhrase())
                .data(data)
                .build();
    }

}