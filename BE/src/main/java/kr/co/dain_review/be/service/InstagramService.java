package kr.co.dain_review.be.service;

import com.google.common.io.Files;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@Service
public class InstagramService {
    @Autowired
    private WebClient webClient;

    @Value("${spring.security.oauth2.client.registration.instagram.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.instagram.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.instagram.redirect-uri}")
    private String redirectUri;

    private static final String TOKEN_URI = "https://graph.facebook.com/v11.0/oauth/access_token";
    private static final String LONG_LIVED_TOKEN_URI = "https://graph.facebook.com/v11.0/oauth/access_token";
    private static final String INSTAGRAM_API_URL = "https://graph.facebook.com/v11.0/";


    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(TOKEN_URI)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("code", code);

        ResponseEntity<Map> response = restTemplate.getForEntity(uriBuilder.toUriString(), Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to obtain access token: " + response.getStatusCode());
        }
    }

    public String getLongLivedAccessToken(String shortLivedToken) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(LONG_LIVED_TOKEN_URI)
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("fb_exchange_token", shortLivedToken);

        ResponseEntity<Map> response = restTemplate.getForEntity(uriBuilder.toUriString(), Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("access_token");
        } else {
            throw new RuntimeException("Failed to obtain long-lived access token: " + response.getStatusCode());
        }
    }

    public Mono<Integer> getReelsCommentsCount(String reelsId, String accessToken) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(INSTAGRAM_API_URL + reelsId + "/comments")
                        .queryParam("access_token", accessToken)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    if (response.containsKey("data")) {
                        var comments = (List<?>) response.get("data");
                        return comments.size();
                    } else {
                        throw new RuntimeException("Failed to retrieve comments count");
                    }
                });
    }
}
