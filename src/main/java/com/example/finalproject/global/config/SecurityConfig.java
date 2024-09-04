package com.example.finalproject.global.config;


import com.example.finalproject.global.jwt.JwtAccessDeniedHandler;
import com.example.finalproject.global.jwt.JwtAuthenticationEntryPoint;
import com.example.finalproject.global.jwt.JwtFilter;
import com.example.finalproject.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	private final CorsConfig corsConfig;
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public BCryptPasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
			// enable h2-console
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeHttpRequests()
			.antMatchers("/user/").hasAnyAuthority("ROLE_ADMIN", "ROLE_BUSINESSES", "ROLE_INFLUENCER", "ROLE_AGENCY")
			.antMatchers("/inf/").hasAnyAuthority("ROLE_ADMIN", "ROLE_INFLUENCER", "ROLE_AGENCY")
			.antMatchers("/bus/").hasAuthority("ROLE_BUSINESSES")
			.antMatchers("/adm/", "/ent/**").hasAuthority("ROLE_ADMIN")
			.anyRequest().permitAll()
			.and()
			.addFilter(corsConfig.corsFilter())
			.addFilterBefore(new JwtFilter(tokenProvider, jwtDecoder()), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri("https://auth-stg.seyfert.kr/oauth2/jwks").build();
	}

}
