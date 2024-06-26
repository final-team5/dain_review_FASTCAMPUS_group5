package kr.co.dain_review.be.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class naver {

    //일 방문자 수 가져오기
    public Integer getVisitorCount(String blogId) throws IOException {
        String url =  "https://blog.naver.com/NVisitorgp4Ajax.nhn?blogId="+blogId;
        Document doc = Jsoup.connect(url).get();
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
        int roundedResult = Math.round(result);
        return roundedResult;
    }

    //좋아요 수 가져오기
    public Integer getLikeCount(String blogId, String logNo) throws IOException {
        String url = "https://m.blog.naver.com/SympathyHistoryList.naver?blogId="+blogId+"&logNo="+logNo;
        Document doc = Jsoup.connect(url).get();
        Integer likeCount = Integer.valueOf(doc.select("span:not([class='num__J35bi'])").text());
        return likeCount;
    }

    //댓글 수 가져오기
    public Integer getCommentCount(String blogId, String logNo) throws IOException {
        String url = "https://m.blog.naver.com/CommentList.naver?blogId="+blogId+"&logNo="+logNo;
        Document doc = Jsoup.connect(url).get();
        Elements scriptTags = doc.getElementsByTag("script");
        Pattern pattern = Pattern.compile("window\\.__INITIAL_STATE__\\s*=\\s*(\\{.*?\\});", Pattern.DOTALL);
        String initialStateJson = null;

        for (Element scriptTag : scriptTags) {
            Matcher matcher = pattern.matcher(scriptTag.html());
            if (matcher.find()) {
                initialStateJson = matcher.group(1);
                break;
            }
        }

        if (initialStateJson != null) {
            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode initialStateNode = objectMapper.readTree(initialStateJson);

            // "totalCount" 값 추출 (JSON 구조에 맞게 경로 변경)
            JsonNode totalCountNode = initialStateNode.path("commentList").path("cboxPostCommentsInfo").path(blogId).path("data").path("comments").path("totalCount");

            if (!totalCountNode.isMissingNode()) {
                int totalCount = totalCountNode.asInt();
                return totalCount;
            } else {
                System.out.println("지정한 경로에서 totalCount 값을 찾을 수 없습니다.");
                return null;
            }
        } else {
            System.out.println("window.__INITIAL_STATE__ 값을 찾을 수 없습니다.");
            return null;
        }
    }

    //블로그 id, 로그 no 추출
    private static String extractUsername(String url) {
        String[] parts = url.split("/");
        // URL에서 마지막 부분이 사용자 이름입니다.
        return parts[parts.length - 1];
    }
}
