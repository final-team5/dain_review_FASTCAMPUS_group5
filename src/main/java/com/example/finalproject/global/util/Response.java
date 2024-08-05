package com.example.finalproject.global.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
	private String resultCode;
	private String resultMessage;
	private T data;
	private Error error;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Error {
		private List<String> errorMessage;
	}
}