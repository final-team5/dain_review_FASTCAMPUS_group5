package kr.co.dain_review.be.util;

import kr.co.dain_review.be.model.main.UserAlarm;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class kakao {

    public static HttpPost getHttpPost(List<NameValuePair> urlParameters){
        String url = "https://kakaoapi.aligo.in:443/akv10/alimtalk/send/ ";
        HttpPost post = new HttpPost (url);
        // add header
        post.setHeader ("User-Agent", "Payapp Java Module");
        post.setEntity (new UrlEncodedFormEntity(urlParameters, Charset.forName("UTF-8")));
        return post;
    }

    public static void sendMessage(UserAlarm data) {
        try {

            String url = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            // POST 요청에 필요한 파라미터 리스트를 생성합니다.
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("apikey", "10frh9nris7zmslcgwyfoy4lh2o9v1j2"));
            urlParameters.add(new BasicNameValuePair("userid", "daingiplan2023"));
            urlParameters.add(new BasicNameValuePair("senderkey", "599dbd9fb8240f589b36cbe6d5c2e8c683887f99"));
            urlParameters.add(new BasicNameValuePair("tpl_code", "TS_4502"));
            urlParameters.add(new BasicNameValuePair("sender", "01092442290"));
            urlParameters.add(new BasicNameValuePair("receiver_1", data.getPhone()));
            urlParameters.add(new BasicNameValuePair("subject_1", "순위알림"));

            String message = "[플레이스 매니저] 오늘 플레이스 순위알림\n" +
                    "해당 메시지는 고객님께서 요청하신 새로운 순위에 해당하는 내용이 있을 경우 발송됩니다.";
            urlParameters.add(new BasicNameValuePair("message_1", message));
            // 요청 파라미터를 POST 메소드에 추가합니다.
            post.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");


            // 요청을 실행하고 응답을 받습니다.
            HttpResponse response = client.execute(post);

            // 응답 내용을 처리합니다. 예를 들어, 응답 내용을 콘솔에 출력할 수 있습니다.
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendMessageRegister(String phone) {
        try {
            String url = "https://kakaoapi.aligo.in/akv10/alimtalk/send/";

            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);

            // POST 요청에 필요한 파라미터 리스트를 생성합니다.
            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("apikey", "10frh9nris7zmslcgwyfoy4lh2o9v1j2"));
            urlParameters.add(new BasicNameValuePair("userid", "daingiplan2023"));
            urlParameters.add(new BasicNameValuePair("senderkey", "599dbd9fb8240f589b36cbe6d5c2e8c683887f99"));
            urlParameters.add(new BasicNameValuePair("tpl_code", "TS_4501"));
            urlParameters.add(new BasicNameValuePair("sender", "01092442290"));
            urlParameters.add(new BasicNameValuePair("receiver_1", phone));
            urlParameters.add(new BasicNameValuePair("subject_1", "회원가입완료"));

            String message = "안녕하세요. 플레이스 매니저 회원가입을 환영합니다!\n" +
                    "\n" +
                    "플레이스 노출 순위 및 데이터는 매일 오후 1:00시에 카카오톡을 통해 업데이트 해드립니다.\n" +
                    "\n" +
                    "하단의 이용방법을 읽어봐주세요.";
            urlParameters.add(new BasicNameValuePair("message_1", message));

            JSONObject button = new JSONObject();
            button.put("name", "플레이스 매니저 이용방법");
            button.put("linkType", "WL");
            button.put("linkTypeName", "웹링크");
            button.put("linkPc", "");
            button.put("linkMo", "https://m.blog.naver.com/successful_laws/223418812309");
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(button);
            JSONObject json = new JSONObject();
            json.put("button", jsonArray);
            urlParameters.add(new BasicNameValuePair("button_1", json.toString()));

            // 요청 파라미터를 POST 메소드에 추가합니다.
            post.setEntity(new UrlEncodedFormEntity(urlParameters, StandardCharsets.UTF_8));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");


            // 요청을 실행하고 응답을 받습니다.
            HttpResponse response = client.execute(post);

            // 응답 내용을 처리합니다. 예를 들어, 응답 내용을 콘솔에 출력할 수 있습니다.
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
