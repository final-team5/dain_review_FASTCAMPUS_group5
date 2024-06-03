package kr.co.dain_review.be.sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BlogCountExample {
//    private String url = "https://blog.naver.com/till0312";
    public static void main(String[] args) throws IOException {
        String url = "https://blog.naver.com/till0312";

        String blogId = extractUsername(url);
        String link = "https://blog.naver.com/NVisitorgp4Ajax.nhn?blogId="+blogId;
        Document doc = Jsoup.connect(link).get();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);
        Elements visitorCounts = doc.select("visitorcnt:not([id="+formattedDate+"])");

        Integer visitor = 0;
        for (Element visitorCount : visitorCounts) {
            String cnt = visitorCount.attr("cnt");
            visitor = visitor + Integer.parseInt(cnt);
        }
        float result = visitor / 4.0f;
        // 결과를 반올림합니다.
        int roundedResult = Math.round(result);
        System.out.println("일일 방문자 수 : "+roundedResult);
    }

    private static String extractUsername(String url) {
        String[] parts = url.split("/");
        // URL에서 마지막 부분이 사용자 이름입니다.
        return parts[parts.length - 1];
    }
}
