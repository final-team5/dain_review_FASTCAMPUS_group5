package kr.co.dain_review.be.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tiktok_api {
    private static final String API_KEY = "YOUR_API_KEY"; // 틱톡 API 키를 여기에 입력하세요
    private static final String VIDEO_ID = "YOUR_VIDEO_ID"; // 가져오려는 비디오 ID를 여기에 입력하세요

    public static void main(String[] args) {
        try {
            String thumbnailUrl = fetchThumbnailUrl(VIDEO_ID);
            System.out.println("Thumbnail URL: " + thumbnailUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String fetchThumbnailUrl(String videoId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.tiktok.com/v1/video/details?video_id=" + videoId + "&api_key=" + API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            return jsonObject.getJSONObject("data").getString("thumbnail_url");
        }
    }
}
