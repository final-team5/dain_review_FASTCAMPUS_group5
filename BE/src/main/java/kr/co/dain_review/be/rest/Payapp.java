package kr.co.dain_review.be.rest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;


@Service
public class Payapp {
    public HttpPost getHttpPost(List<NameValuePair> urlParameters){
        String url = "http://api.payapp.kr/oapi/apiLoad.html";
        HttpPost post = new HttpPost (url);
        // add header
        post.setHeader ("User-Agent", "Payapp Java Module");
        post.setEntity (new UrlEncodedFormEntity(urlParameters, Charset.forName("UTF-8")));
        return post;
    }
}
