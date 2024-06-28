package kr.co.dain_review.be.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class youtube {

    private static final String API_KEY = "AIzaSyBhQqhQ3AszlX2EEjmRM7d5U9otm8yENBo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "API Project";


    public static void getSubscriber(String link) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        String channelId = getChannelID(link); // Replace with your channel ID

        YouTube.Channels.List request = youtubeService.channels()
                .list(Arrays.asList("statistics"));
        ChannelListResponse response = request.setId(Arrays.asList(channelId))
                .setKey(API_KEY)
                .execute();

        Channel channel = response.getItems().get(0);
        System.out.println("Subscriber Count: " + channel.getStatistics().getSubscriberCount());
    }


    public static String getChannelID(String url) throws GeneralSecurityException, IOException {
        String handle = url.split("@")[1];
        YouTube youtubeService = getService();
        YouTube.Search.List searchRequest = youtubeService.search()
                .list(Arrays.asList("snippet"))
                .setType(Arrays.asList("channel"))
                .setQ(handle)
                .setKey(API_KEY);

        SearchListResponse searchResponse = searchRequest.execute();
        List<SearchResult> searchResults = searchResponse.getItems();
        String channelId = null;
        if (searchResults.isEmpty()) {
            System.out.println("No channel found with handle: " + handle);
        } else {
            SearchResult result = searchResults.get(0);
            System.out.println("Channel ID: " + result.getSnippet().getChannelId());
            channelId = result.getSnippet().getChannelId();
            System.out.println("Channel Title: " + result.getSnippet().getTitle());
        }
        return channelId;
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
