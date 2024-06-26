package kr.co.dain_review.be.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    public static String filePath;
    public static String ffmpegPath;
    public static String filePath2;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000", "http://localhost:3000")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()
                )
                .allowCredentials(true);
    }

    @Override
    // 특정 경로와 로컬을 이어주고 해당 경로가 사용될때 캐시관련 헤더와 함께 내려줌
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = "C:/Users/start/IdeaProjects/mailer_rctown/BE/src/main/resources/static/";
            filePath2 = "C:/Users/start/IdeaProjects/mailer_rctown/BE/src/main/resources/static/";
        }
        else {
            filePath = "";
            filePath2 = "/mnt/extra02/home/mailer/BE/";
        }
    }
}