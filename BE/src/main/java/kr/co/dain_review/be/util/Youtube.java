package kr.co.dain_review.be.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import kr.co.dain_review.be.model.campaign.Result;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Youtube<getVideoIdFromUrl> {

    private static final String API_KEY = "AIzaSyBhQqhQ3AszlX2EEjmRM7d5U9otm8yENBo";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "API Project";


    public static void getSubscriber(String url) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();
        String channelId = getChannelID(url); // Replace with your channel ID

        YouTube.Channels.List request = youtubeService.channels()
                .list(Arrays.asList("statistics"));
        ChannelListResponse response = request.setId(Arrays.asList(channelId))
                .setKey(API_KEY)
                .execute();

        Channel channel = response.getItems().get(0);
        System.out.println("Subscriber Count: " + channel.getStatistics().getSubscriberCount());
    }

    public static Result getResult(String url) {
        try {
            Result result = new Result();
            YouTube youtubeService = getService();
            String videoId = getVideoIdFromUrl(url);

            // Define and execute the API request
            YouTube.Videos.List request = youtubeService.videos()
                    .list(Arrays.asList("id,statistics,snippet"))
                    .setId(Arrays.asList(videoId))
                    .setKey(API_KEY);

            VideoListResponse response = request.execute();
            List<Video> videoList = response.getItems();


            String channelId = null;

            if (videoList != null && !videoList.isEmpty()) {
                Video video = videoList.get(0);
//                BigInteger viewCount = video.getStatistics().getViewCount();
                BigInteger likeCount = video.getStatistics().getLikeCount();
                BigInteger commentCount = video.getStatistics().getCommentCount();
                String videoTitle = video.getSnippet().getTitle();
                channelId = video.getSnippet().getChannelId();

                result.setLike(likeCount.intValue());
                result.setComment(commentCount.intValue());
                result.setTitle(videoTitle);
            } else {
                System.out.println("Video not found.");
            }
            if(channelId!=null) {
                YouTube.Channels.List channelRequest = youtubeService.channels().list(Arrays.asList("snippet"));
                channelRequest.setId(Arrays.asList(channelId));
                channelRequest.setKey(API_KEY);
                ChannelListResponse channelResponse = channelRequest.execute();
                List<Channel> channelList = channelResponse.getItems();
                if (channelList != null && !channelList.isEmpty()) {
                    Channel channel = channelList.get(0);
                    String channelTitle = channel.getSnippet().getTitle();
                    result.setNickname(channelTitle);
                }
            }
            return result;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public static Result getResultShorts(String url) {
        try {
            Result result = new Result();

            String videoId = getVideoId(url);
            YouTube youtubeService = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            // Define and execute the API request for the video details
            YouTube.Videos.List videoRequest = youtubeService.videos().list(Arrays.asList("snippet,statistics"));
            videoRequest.setId(Arrays.asList(videoId));
            videoRequest.setKey(API_KEY);
            VideoListResponse videoResponse = videoRequest.execute();
            List<Video> videoList = videoResponse.getItems();

            Video video = videoList.get(0);
            String videoTitle = video.getSnippet().getTitle();
            String channelId = video.getSnippet().getChannelId();
            Integer likeCount = video.getStatistics().getLikeCount().intValue();
            Integer commentCount = video.getStatistics().getCommentCount().intValue();

            result.setTitle(videoTitle);
            result.setLike(likeCount);
            result.setTitle(videoTitle);
            // Define and execute the API request for the channel details
            YouTube.Channels.List channelRequest = youtubeService.channels().list(Arrays.asList("snippet"));
            channelRequest.setId(Arrays.asList(channelId));
            channelRequest.setKey(API_KEY);
            ChannelListResponse channelResponse = channelRequest.execute();
            List<Channel> channelList = channelResponse.getItems();

            if (channelList.isEmpty()) {
                System.out.println("No channel found with the given ID.");
                return null;
            }

            Channel channel = channelList.get(0);
            String channelTitle = channel.getSnippet().getTitle();

            result.setTitle(videoTitle);
            result.setNickname(channelTitle);
            result.setLike(likeCount);
            result.setComment(commentCount);
            return result;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    private static String getVideoIdFromUrl(String videoUrl) {
        String videoId = null;
        if (videoUrl != null && videoUrl.contains("v=")) {
            videoId = videoUrl.substring(videoUrl.indexOf("v=") + 2);
            if (videoId.contains("&")) {
                videoId = videoId.substring(0, videoId.indexOf("&"));
            }
        }
        return videoId;
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

    public static String getVideoId(String url){
        String patternString = "https?://(www\\.)?youtube\\.com/shorts/([^/?&]+)";

        // 패턴 컴파일
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(url);

        // 매칭 확인 및 video_id 추출
        if (matcher.find()) {
            String videoId = matcher.group(2);
            System.out.println("Video ID: " + videoId);
            return videoId;
        } else {
            System.out.println("No video ID found in the provided link.");
            return null;
        }
    }

    public static YouTube getService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void main(String[] pp){
        Result result = getResult("https://www.youtube.com/shorts/bMmXx6Gpkmo");
        System.out.println(result);
    }



}
