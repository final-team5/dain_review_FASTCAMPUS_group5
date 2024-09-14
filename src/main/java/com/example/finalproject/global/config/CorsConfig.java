package com.example.finalproject.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.setAllowedOrigins(Arrays.asList(
//				"http://localhost:8000",
//				"http://localhost:3000",
//				"https://dain-review.vercel.app"
//		));
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addExposedHeader("Authorization");

		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(source);
	}
}
