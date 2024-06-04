package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.UserMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.main.*;
import kr.co.dain_review.be.model.user.*;
import kr.co.dain_review.be.util.FileUtils;
import kr.co.dain_review.be.util.smtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(String email, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("type", type);
        return userMapper.getUser(map);
    }

    public boolean checkNickname(String nickname) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nickname", nickname);
        return userMapper.checkNickname(map);
    }

    public boolean checkAuthentication(String email, Integer auth) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("auth", auth);
        map.put("email", email);
        return userMapper.checkAuthentication(map);
    }

    public void register(Register register) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(register, HashMap.class);
        String id = String.valueOf(UUID.randomUUID());
        String fileName = FileUtils.setNewName(register.getProfile());
        FileUtils.saveFile(register.getProfile(), id+"/"+fileName);
        map.put("id", id);
        map.put("profile", fileName);
        map.put("email", register.getEmail());
        map.put("pw", register.getPw());
        map.put("name", register.getName());
        map.put("phone", register.getPhone());
        map.put("signUpSource", register.getSignUpSource());
        map.put("blog", register.isBlogLink());
        map.put("instagram", register.isInstagramLink());
        map.put("youtube", register.isYoutubeLink());
        map.put("tiktok", register.isTiktokLink());
        map.put("other", register.isOtherLink());
        map.put("postalCode", register.getPostalCode());
        map.put("address", register.getAddress());
        map.put("addressDetail", register.getAddressDetail());
        map.put("nickname", register.getNickname());
        map.put("birthdate", register.getBirthdate());
        map.put("gender", register.getGender());
        map.put("company", register.getCompany());
        map.put("role", register.getRole());
        userMapper.insert(map);
        Integer userSeq = userMapper.getSeq(map);
        map.put("userSeq", userSeq);
        if(register.isBlogLink()){
            map.put("link", register.getBlogLink());
            map.put("type", 1);
            userMapper.insertPlatform(map);
        }
        if(register.isInstagramLink()){
            map.put("link", register.getInstagramLink());
            map.put("type", 2);
            userMapper.insertPlatform(map);
        }
        if(register.isYoutubeLink()){
            map.put("link", register.getYoutubeLink());
            map.put("type", 3);
            userMapper.insertPlatform(map);
        }
        if(register.isTiktokLink()){
            map.put("link", register.getTiktokLink());
            map.put("type", 4);
            userMapper.insertPlatform(map);
        }
        if(register.isOtherLink()){
            map.put("link", register.getOtherLink());
            map.put("type", 5);
            userMapper.insertPlatform(map);
        }
    }


    public String findEmail(FindEmail findEmail) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(findEmail, HashMap.class);
        String email = userMapper.findEmail(map);
        return maskEmail(email);
    }

    public String findPassword(FindPassword findPassword) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(findPassword, HashMap.class);
        return userMapper.findPassword(map);
    }

    public Integer getAuthenticationNumber(UserVerification emailVerification) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(emailVerification, HashMap.class);
        return userMapper.getAuthenticationNumber(map);
    }

    public void changePassword(Integer userSeq, ChangePassword changePassword) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(changePassword, HashMap.class);
        map.put("userSeq", userSeq);
        userMapper.changePassword(map);
    }


    @Async
    public void emailVerification(Email email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email.getEmail());
        userMapper.deleteVerification(map);
        int authNUm = (int)(Math.random() * 900000) + 100000;
        map.put("authNUm", authNUm);
        LocalDateTime now = LocalDateTime.now(); // 현재 시간을 가져옴
        LocalDateTime thirtyMinutesLater = now.plusMinutes(30); // 현재 시간에 30분을 더함
        map.put("expireDate", thirtyMinutesLater);
        userMapper.insertVerification(map);
        smtp.emailSend(email.getEmail(), authNUm);
    }

    public boolean getFindUser(UserVerification userVerification) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(userVerification, HashMap.class);
        return userMapper.getFindUser(map);
    }

    public void userCertification(UserVerification userVerification) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", userVerification.getEmail());
        map.put("authNum", userVerification.getAuth());
        userMapper.userCertification(map);
    }

    public void emailCertification(UserVerification emailVerification) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(emailVerification, HashMap.class);
        userMapper.emailCertification(map);
    }

    public boolean findPasswordVerification(FindPassword findPassword) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(findPassword, HashMap.class);
        return userMapper.findPasswordVerification(map);
    }

    public void setNewPassword(FindPassword findPassword) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(findPassword, HashMap.class);
        userMapper.setNewPassword(map);
        userMapper.deleteVerification(map);
    }

    public boolean checkEmail(String email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        return userMapper.checkEmail(map);
    }

    public boolean checkId(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return userMapper.checkId(map);
    }

    public static String maskEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return email; // '@'가 없으면 변경 없이 반환
        }

        String localPart = email.substring(0, atIndex); // '@' 앞 부분
        String domainPart = email.substring(atIndex); // '@' 포함 뒷부분

        // 첫 4글자를 제외하고 '*'로 치환하기 위한 반복 문자열 생성
        String stars = repeat('*', localPart.length() - 4);
        String maskedLocalPart = localPart.length() > 4
                ? localPart.substring(0, 4) + stars
                : localPart; // 길이가 4 이하면 변경 없이 반환

        return maskedLocalPart + domainPart;
    }

    // '*' 문자를 n번 반복하는 메서드
    public static String repeat(char ch, int count) {
        if (count < 0) {
            return ""; // 반복 횟수가 0 미만이면 빈 문자열 반환
        }
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public boolean findEmailCheck(FindEmail findEmail) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(findEmail, HashMap.class);
        return userMapper.findEmailCheck(map);
    }

    public boolean checkPhone(String phone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        return userMapper.checkPhone(map);
    }



    public void Withdrawal(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", userSeq);
        userMapper.Withdrawal(map);
    }


    public boolean checkSocialUser(String email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        return userMapper.checkSocialUser(map);
    }

    public ArrayList<User> getList(Search search) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return userMapper.getList(map);
    }

    public Integer getListCount(Search search){
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return userMapper.getListCount(map);
    }


    public User getDetail(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        return userMapper.getDetail(map);
    }

    public void setUpdate(AdminUserUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        userMapper.update(map);
    }

    public void setInsert(AdminUserInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        userMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            userMapper.delete(map);
        }
    }

    public UserProfile getProfile(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return userMapper.selectProfile(map);
    }

    public void setProfile(UserUpdate update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("seq", userSeq);
        userMapper.updateProfile(map);
    }
}
