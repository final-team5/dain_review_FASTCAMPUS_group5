package kr.co.dain_review.be.sample;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;

public class YouTubeApiExample {
    private static final String API_KEY = "AIzaSyBhQqhQ3AszlX2EEjmRM7d5U9otm8yENBo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "API Project";


    public static void main(String[] args) {
        String url = "https://www.youtube.com/@dragonpiece"; // 채널 URL을 입력하세요.
        try {
            Document doc = Jsoup.connect(url).get();
            // 페이지 내의 모든 스크립트 태그를 탐색합니다.
            Elements scripts = doc.getElementsByTag("script");
//            System.out.println(scripts);

            // 스크립트 태그 내에서 채널 ID를 찾는 패턴입니다.
            // 스크립트 태그의 내용 중 위 패턴과 일치하는 부분을 찾습니다.
            for (Element script : scripts) {
                String scriptText = script.html();

                if (scriptText.contains("externalId")) {
                    String pattern = "\"externalId\":\"[^\"]+\"";
                    java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
                    java.util.regex.Matcher m = p.matcher(scriptText);
                    if (m.find()) {
                        String channelId = m.group(0).split(":")[1].replace("\"", "");
                        System.out.println("Channel ID: " + channelId);
                        channelInfo(channelId);
                        break;
                    }
                } else if (scriptText.contains("channelId")) {
                    String pattern = "\"channelId\":\"[^\"]+\"";
                    java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
                    java.util.regex.Matcher m = p.matcher(scriptText);
                    if (m.find()) {
                        String channelId = m.group(0).split(":")[1].replace("\"", "");
                        System.out.println("Channel ID: " + channelId);
                        channelInfo(channelId);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void channelInfo(String channelId) {
        try {
            YouTube youtubeService = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // 여기에 API 키와 채널 ID를 입력하세요

            YouTube.Channels.List request = youtubeService.channels()
                    .list(Collections.singletonList("statistics"));
            request.setId(Collections.singletonList(channelId));
            request.setKey(API_KEY);

            ChannelListResponse response = request.execute();
            System.out.println(response);
            response.getItems().forEach(channel -> {
                System.out.println("Subscriber Count: " + channel.getStatistics().getSubscriberCount());
                System.out.println("video Count: " + channel.getStatistics().getVideoCount());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
