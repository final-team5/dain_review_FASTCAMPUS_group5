package com.example.finalproject.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerSecurityConfig implements WebMvcConfigurer {

    // Swagger-ui 경로 검증 ignore 처리 -> Spring Security 설정 완료 후 해당 코드 활용해 주세요 태민님
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .csrf().disable();
//
//        return http.build();
//    }

}
