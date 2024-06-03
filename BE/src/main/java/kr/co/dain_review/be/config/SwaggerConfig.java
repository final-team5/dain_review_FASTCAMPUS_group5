package kr.co.dain_review.be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    private static final String API_NAME = "review API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "review API 명세서";

    @Override public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/"); }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo()) //apiInfo 삽입
                .select() //ApiSelectorBuilder를 생성
                .apis(RequestHandlerSelectors.basePackage("kr.co.dain_review.be.controller")) //API 범위 지정 (해당 패키지 내부에 있는 모든 Request 출력)
                .paths(PathSelectors.any()) //스웨거 html 페이지에 모든 URL 제공
                //.paths(PathSelectors.ant("/test/**")) 스웨거 html 페이지에 특정 api만 보여주고 싶다면 해당 부분 설정
                .build()
                .apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {  //ApiInfo 설정
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }




}