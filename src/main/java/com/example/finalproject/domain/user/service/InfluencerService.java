package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.user.dto.response.InfluencerDetailResponse;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${instagram.business.id}")
    private String instagramBusinessId;

    @Value("${instagram.access.token}")
    private String instagramAccessToken;

    @Value("${tiktok.access.token}")
    private String tiktokAccessToken;

    @Value("${youtube.api.key}")
    private String youtubeApiKey;

    @Autowired
    public InfluencerService(InfluencerRepository influencerRepository) {
        this.influencerRepository = influencerRepository;
    }

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    /**
     * 주어진 체험단 시퀀스에 연결된 모든 인플루언서의 상세 정보를 조회하는 메서드
     *
     * @param campaignSeq 체험단 시퀀스 번호
     * @return 해당 체험단에 연결된 인플루언서들의 상세 정보 목록
     */
    public List<InfluencerDetailResponse> getInfluencersForCampaign(Long campaignSeq) {
        List<Influencer> influencers = influencerRepository.findByCampaignSeq(campaignSeq);
        return influencers.stream()
                .map(influencer -> convertToInfluencerDetailResponse(influencer, "인스타그램"))
                .collect(Collectors.toList());
    }

    /**
     * 인플루언서 엔티티를 상세 정보 DTO로 변환하는 메서드
     *
     * @param influencer         인플루언서 엔티티 객체
     * @param designatedPlatform 인플루언서 정보를 표시할 기본 플랫폼
     * @return 인플루언서의 상세 정보를 담은 DTO 객체
     */
    private InfluencerDetailResponse convertToInfluencerDetailResponse(Influencer influencer, String designatedPlatform) {
        String platformUrl = getPlatformUrl(designatedPlatform, influencer);
        String platformRank = getPlatformRank(designatedPlatform, influencer);
        Integer followerCount = getPlatformFollowerCount(designatedPlatform, influencer);
        Integer application = getLatestApplicationForInfluencer(influencer.getUser().getSeq());

        return new InfluencerDetailResponse(
                influencer.getSeq(),
                influencer.getUser().getName(),
                application,  // 예시로 '1'을 사용
                platformRank,
                platformUrl,
                followerCount,
                influencer.getUser().getCancel(),
                influencer.getUser().getPhone(),
                null,  // 리뷰 등록일 예시
                null   // 첨부파일 예시
        );
    }

    /**
     * 주어진 인플루언서의 최신 캠페인 신청 정보(application)를 조회합니다.
     *
     * @param userSeq 인플루언서의 사용자 시퀀스 번호
     * @return 최신 캠페인 신청 정보(application 값)
     */
    private Integer getLatestApplicationForInfluencer(Integer userSeq) {
        return influencerRepository.findLatestApplicationByUserSeq(userSeq).orElse(0);  // 기본값은 0
    }

    /**
     * 지정된 플랫폼에 따라 인플루언서의 팔로워 수를 조회하는 메서드
     *
     * @param platform   조회할 플랫폼 이름
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 해당 플랫폼의 팔로워 수, 해당 플랫폼 정보가 없을 경우 0 반환
     */
    private int getPlatformFollowerCount(String platform, Influencer influencer) {
        switch (platform) {
            case "인스타그램":
                return getInstagramFollowers(influencer);
            case "유튜브":
                return getYouTubeSubscribers(influencer);
            case "틱톡":
                return getTikTokFollowers(influencer);
            case "블로그":
                return getBlogVisitors(influencer);
            default:
                throw new IllegalArgumentException("지원하지 않는 플랫폼입니다: " + platform);
        }
    }


    /**
     * 지정된 플랫폼에 따라 인플루언서의 플랫폼 URL을 반환하는 메서드
     *
     * @param platform   조회할 플랫폼 이름 (예: "인스타그램", "유튜브")
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 해당 플랫폼의 URL 문자열, 해당 플랫폼 정보가 없을 경우 null 반환
     */
    private String getPlatformUrl(String platform, Influencer influencer) {
        switch (platform) {
            case "인스타그램":
                return influencer.getInstagramLink();
            case "유튜브":
                return influencer.getYoutubeLink();
            case "틱톡":
                return influencer.getTiktokLink();
            case "블로그":
                return influencer.getBlogLink();
            default:
                return null;
        }
    }

    /**
     * 지정된 플랫폼에 따라 인플루언서의 플랫폼 등급을 계산하는 메서드
     *
     * @param platform   조회할 플랫폼 이름
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 팔로워 또는 방문자 수에 따라 "초급", "중급", "고급", "프리미어" 중 하나를 반환
     */
    private String getPlatformRank(String platform, Influencer influencer) {
        Integer followers;
        switch (platform) {
            case "인스타그램":
                followers = influencer.getInstagramFollower();
                break;
            case "유튜브":
                followers = influencer.getYoutubeSubscriber();
                break;
            case "틱톡":
                followers = influencer.getTiktokFollower();
                break;
            case "블로그":
                followers = influencer.getBlogVisitors();
                break;
            default:
                followers = 0;
                break;
        }
        if (followers == null || followers == 0) {
            return "초급";
        }
        return followers >= 1000000 ? "프리미어" : followers >= 100000 ? "고급" : followers >= 10000 ? "중급" : "초급";
    }

    /**
     * Instagram의 Business Discovery API를 사용하여 팔로워 수를 조회하는 메서드
     *
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 해당 플랫폼의 팔로워 수, 해당 플랫폼 정보가 없을 경우 0 반환
     */
    public int getInstagramFollowers(Influencer influencer) {
        if (influencer.getInstagramLink() == null || influencer.getInstagramLink().isEmpty()) {
            throw new IllegalArgumentException("인스타그램 링크는 필수입니다.");
        }

        String username = extractUsername(influencer.getInstagramLink());
        String endpoint = "https://graph.facebook.com/v13.0/" + instagramBusinessId +
                "/business_discovery.username(" + username + "){followers_count}";

        try {
            InstagramBusinessDiscovery response = restTemplate.getForObject(
                    endpoint + "?access_token=" + instagramAccessToken, InstagramBusinessDiscovery.class);

            if (response != null) {
                return response.getFollowersCount();
            } else {
                throw new IllegalStateException("인스타그램 데이터를 찾을 수 없습니다.");
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("인스타그램 API 요청 실패: " + e.getMessage());
        }
    }

    private String extractUsername(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 URL입니다.");
        }
        return url.substring(url.lastIndexOf('/') + 1).replaceAll("/", "");
    }

    private static class InstagramBusinessDiscovery {
        private int followers_count;

        public int getFollowersCount() {
            return followers_count;
        }
    }

    /**
     * TikTok 팔로워 수를 조회하는 메서드
     *
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 팔로워 수를 직접 반환, 예외 발생 시 예외 처리
     */
    public int getTikTokFollowers(Influencer influencer) {
        if (influencer.getTiktokLink() == null || influencer.getTiktokLink().isEmpty()) {
            throw new IllegalArgumentException("틱톡 링크는 필수입니다.");
        }

        String openId = extractTikTokOpenId(influencer.getTiktokLink());
        String endpoint = "https://business-api.tiktok.com/open_api/v1.2/user/info/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tiktokAccessToken);  // Bearer 토큰으로 인증 정보를 설정
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<TikTokUserResponse> response = restTemplate.exchange(
                    endpoint + "?open_id=" + openId + "&access_token=" + tiktokAccessToken,
                    HttpMethod.GET, entity, TikTokUserResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getData().getFollowersCount();
            } else {
                throw new IllegalStateException("틱톡 데이터를 찾을 수 없습니다.");
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("틱톡 API 요청 실패: " + e.getMessage());
        }
    }

    /**
     * 틱톡 프로필 URL에서 open_id를 추출하는 메서드
     *
     * @param url 틱톡 프로필 URL
     * @return 추출된 open_id
     */
    private String extractTikTokOpenId(String url) {
        if (url == null || !url.contains("@")) {
            throw new IllegalArgumentException("유효하지 않은 틱톡 URL입니다.");
        }
        return url.substring(url.indexOf('@') + 1).replaceAll("[^a-zA-Z0-9_]", "");
    }

    private static class TikTokUserResponse {
        private TikTokUserData data;

        public TikTokUserData getData() {
            return data;
        }
    }

    private static class TikTokUserData {
        private int followers_count;

        public int getFollowersCount() {
            return followers_count;
        }
    }

    /**
     * YouTube 구독자 수를 조회하는 메서드
     *
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 구독자 수를 직접 반환, 예외 발생 시 예외 처리
     */
    public int getYouTubeSubscribers(Influencer influencer) {
        if (influencer.getYoutubeLink() == null || influencer.getYoutubeLink().isEmpty()) {
            throw new IllegalArgumentException("유튜브 링크는 필수입니다.");
        }

        String channelId = extractYouTubeChannelId(influencer.getYoutubeLink());
        String endpoint = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + channelId +
                "&key=" + youtubeApiKey;

        try {
            ResponseEntity<YouTubeChannelResponse> response = restTemplate.getForEntity(
                    endpoint, YouTubeChannelResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                YouTubeStatistics statistics = response.getBody().getItems().get(0).getStatistics();
                return Integer.parseInt(statistics.getSubscriberCount());
            } else {
                throw new IllegalStateException("유튜브 데이터를 찾을 수 없습니다.");
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException("유튜브 API 요청 실패: " + e.getMessage());
        }
    }

    /**
     * 유튜브 URL에서 채널 ID를 추출하는 메서드
     *
     * @param url 유튜브 채널 URL
     * @return 추출된 채널 ID
     */
    private String extractYouTubeChannelId(String url) {
        return url.substring(url.lastIndexOf('/') + 1).replaceAll("/", "");
    }

    private static class YouTubeChannelResponse {
        private List<YouTubeItem> items;

        public List<YouTubeItem> getItems() {
            return items;
        }
    }

    private static class YouTubeItem {
        private YouTubeStatistics statistics;

        public YouTubeStatistics getStatistics() {
            return statistics;
        }
    }

    private static class YouTubeStatistics {
        private String subscriberCount;

        public String getSubscriberCount() {
            return subscriberCount;
        }
    }

    /**
     * 네이버 블로그 방문자 수를 조회하는 메서드
     *
     * @param influencer 조회할 인플루언서의 엔티티 객체
     * @return 블로그 방문자 수
     */
    public int getBlogVisitors(Influencer influencer) {
        String blogUrl = influencer.getBlogLink();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String apiUrl = "https://openapi.naver.com/v1/urlinfo?url=" + blogUrl;

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl, HttpMethod.GET, entity, String.class);
            return parseVisitorsFromResponse(response.getBody());
        } catch (Exception e) {
            throw new IllegalStateException("블로그 정보 요청 실패: " + e.getMessage());
        }
    }

    /**
     * API 응답에서 방문자 수를 파싱
     *
     * @param responseBody 응답 바디
     * @return 파싱된 방문자 수
     */
    private int parseVisitorsFromResponse(String responseBody) {
        // TODO: 파싱 구현
        return 0;
    }
}