package kr.co.dain_review.be.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tiktok {
    public static Integer getFollowerCount(String url) {
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
                return Integer.parseInt(followerCount);
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
        return 0;
    }
}
