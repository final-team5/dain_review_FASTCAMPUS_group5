package kr.co.dain_review.be.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import kr.co.dain_review.be.model.campaign.Result;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class instagram {

    public static void getFollower(String url) {
        // 시스템 출력 스트림을 임시로 변경하여 모든 메시지를 억제
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // 아무 작업도 하지 않음
            }
        }));
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
                // 아무 작업도 하지 않음
            }
        }));
        String pageSource = null;
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // JavaScript와 CSS를 활성화
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.waitForBackgroundJavaScriptStartingBefore(10000);

            // TikTok 사용자 페이지 URL로 이동
            HtmlPage page = webClient.getPage(url);

            // JavaScript 실행이 완료될 때까지 대기
            webClient.waitForBackgroundJavaScript(10000);

            // 페이지의 HTML 소스 가져오기
            pageSource = page.asXml();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 시스템 출력 스트림 복원
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
//        System.out.println(pageSource);
        // playCount 값을 추출하기 위한 정규 표현식 패턴
        Document doc = Jsoup.parse(pageSource);
        Element metaTag = doc.selectFirst("meta[name=description]");
        if (metaTag != null) {
            String content = metaTag.attr("content");
            System.out.println("Meta content: " + content);


            String pattern = "([\\d,.]+[KM]?) Followers, ([\\d,.]+[KM]?) Following, ([\\d,.]+[KM]?) Posts";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(content);

            if (matcher.find()) {
                String followers = matcher.group(1);
                String following = matcher.group(2);
                String posts = matcher.group(3);

                System.out.println("Followers: " + convertToInt(followers));
                System.out.println("Following: " + convertToInt(following));
                System.out.println("Posts: " + convertToInt(posts));
            } else {
                System.out.println("Pattern not found");
            }
        }
    }


    private static int convertToInt(String value) {
        value = value.replace(",", ""); // 쉼표 제거
        if (value.endsWith("K")) {
            return (int)(Double.parseDouble(value.replace("K", "")) * 1_000);
        } else if (value.endsWith("M")) {
            return (int)(Double.parseDouble(value.replace("M", "")) * 1_000_000);
        } else {
            return Integer.parseInt(value);
        }
    }


    public static Result getResult(String url) {
        Result result = new Result();
        // 시스템 출력 스트림을 임시로 변경하여 모든 메시지를 억제
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;
        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
                // 아무 작업도 하지 않음
            }
        }));
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
                // 아무 작업도 하지 않음
            }
        }));
        String pageSource = null;
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            // JavaScript와 CSS를 활성화
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.waitForBackgroundJavaScriptStartingBefore(10000);

            // TikTok 사용자 페이지 URL로 이동
            HtmlPage page = webClient.getPage(url);

            // JavaScript 실행이 완료될 때까지 대기
            webClient.waitForBackgroundJavaScript(10000);

            // 페이지의 HTML 소스 가져오기
            pageSource = page.asXml();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 시스템 출력 스트림 복원
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
//        System.out.println(pageSource);
        // playCount 값을 추출하기 위한 정규 표현식 패턴
        Document doc = Jsoup.parse(pageSource);
        Element metaTag = doc.selectFirst("meta[name=description]");
        if (metaTag != null) {
            String content = metaTag.attr("content");
            System.out.println("Meta content: " + content);

            String pattern = "(\\d+[KM]?) likes, (\\d+[KM]?) comments - ([^\\s]+) on [^:]+: \"([^\"]+)\"";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(content);

            if (matcher.find()) {
                String likesStr = matcher.group(1);
                String commentsStr = matcher.group(2);
                String nickname = matcher.group(3);
                String title = matcher.group(4);


                int likes = convertToInt(likesStr);
                int comments = convertToInt(commentsStr);
                result.setComment(comments);
                result.setLike(likes);
                result.setTitle(title);
                result.setNickname(nickname);
            } else {
                System.out.println("Pattern not found");
            }
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] aa){
        Result result = getResult("https://www.instagram.com/reel/C8s6LlTvRQU/");
        System.out.println(result);
    }
}
