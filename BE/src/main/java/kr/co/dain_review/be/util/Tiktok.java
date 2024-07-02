package kr.co.dain_review.be.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import kr.co.dain_review.be.model.campaign.Result;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tiktok {
    public static Result getFollowerCount(String url) {
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

        Result result = new Result();

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
            String pageSource = page.asXml();

            // followerCount 값을 추출하기 위한 정규 표현식 패턴
            Pattern pattern = Pattern.compile("\"followerCount\":(\\d+)");
            Matcher matcher = pattern.matcher(pageSource);

            if (matcher.find()) {
                String followerCount = matcher.group(1);
                // 임시로 시스템 출력 스트림을 복원하여 결과 출력
                System.setOut(originalOut);
                System.setErr(originalErr);
                System.out.println("Followers: " + followerCount);
                Integer.parseInt(followerCount);
            } else {
                // 임시로 시스템 출력 스트림을 복원하여 결과 출력
                System.setOut(originalOut);
                System.setErr(originalErr);
                System.out.println("Follower count not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 시스템 출력 스트림 복원
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
        return result;
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
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }

        // playCount 값을 추출하기 위한 정규 표현식 패턴
        Pattern playCountPattern = Pattern.compile("\"playCount\":(\\d+)");
        Matcher playCountMatcher = playCountPattern.matcher(pageSource);
        String playCount = null;
        if (playCountMatcher.find()) {
            playCount = playCountMatcher.group(1);
        }

        // diggCount 값을 추출하기 위한 정규 표현식 패턴
        Pattern diggCountPattern = Pattern.compile("\"diggCount\":(\\d+)");
        Matcher diggCountMatcher = diggCountPattern.matcher(pageSource);
        String diggCount = null;
        if (diggCountMatcher.find()) {
            diggCount = diggCountMatcher.group(1);
            result.setLike(Integer.valueOf(diggCount));
        }

        // commentCount 값을 추출하기 위한 정규 표현식 패턴
        Pattern commentCountPattern = Pattern.compile("\"commentCount\":(\\d+)");
        Matcher commentCountMatcher = commentCountPattern.matcher(pageSource);
        String commentCount = null;
        if (commentCountMatcher.find()) {
            commentCount = commentCountMatcher.group(1);
            result.setComment(Integer.valueOf(commentCount));
        }


        try {
            // itemStruct 부분 추출
            Pattern pattern = Pattern.compile("\"itemStruct\"\\s*:\\s*\\{(.*?)\\}\\s*,", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(pageSource);

            if (matcher.find()) {
                String itemStructContent = matcher.group(1);

                // desc 키의 값 추출
                Pattern descPattern = Pattern.compile("\"desc\"\\s*:\\s*\"(.*?)\"");
                Matcher descMatcher = descPattern.matcher(itemStructContent);

                if (descMatcher.find()) {
                    String title = descMatcher.group(1);
                    result.setTitle(title);
                } else {
                    System.out.println("desc key not found.");
                }
            } else {
                System.out.println("itemStruct not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // itemStruct 부분 추출
            Pattern pattern = Pattern.compile("\"author\"\\s*:\\s*\\{(.*?)\\}\\s*,", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(pageSource);

            if (matcher.find()) {
                String itemStructContent = matcher.group(1);

                // desc 키의 값 추출
                Pattern descPattern = Pattern.compile("\"nickname\"\\s*:\\s*\"(.*?)\"");
                Matcher descMatcher = descPattern.matcher(itemStructContent);

                if (descMatcher.find()) {
                    String nickname = descMatcher.group(1);
                    result.setNickname(nickname);
                } else {
                    System.out.println("desc key not found.");
                }
            } else {
                System.out.println("itemStruct not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] aa){
        Result result = getResult("https://www.tiktok.com/@kb0780/video/7386779084589731078?is_from_webapp=1&sender_device=pc&web_id=7381267125019461137");
        System.out.println(result);
    }
}
