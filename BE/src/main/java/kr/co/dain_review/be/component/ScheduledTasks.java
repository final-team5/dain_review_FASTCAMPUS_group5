package kr.co.dain_review.be.component;

import kr.co.dain_review.be.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private UserService userService;            //키워드 관련 서비스

    @Scheduled(cron = "0 0 1 * * ?")
    public void sendMessage() {
        System.out.println("실행되는 작업: 방문자, 구독자, 팔로워 업데이트");
        //순위 알림 발송
        userService.updateFollower();
        userService.updateVisitors();
        userService.updateSubscriber();
    }

}
