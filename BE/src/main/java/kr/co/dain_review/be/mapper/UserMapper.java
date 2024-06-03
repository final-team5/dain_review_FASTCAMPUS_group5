package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.user.User;
import kr.co.dain_review.be.model.user.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface UserMapper {

    User getUser(HashMap map);

    boolean checkNickname(HashMap map);

    boolean checkAuthentication(HashMap map);

    void register(HashMap map);

    boolean checkPhone(HashMap<String, Object> map);

    void updateUser(HashMap<String, Object> map);

    String findEmail(HashMap<String, Object> map);

    void setVerification(HashMap<String, Object> map);

    Integer getAuthenticationNumber(HashMap<String, Object> map);

    String findPassword(HashMap<String, Object> map);

    boolean getFindUser(HashMap<String, Object> map);

    void userCertification(HashMap<String, Object> map);

    void emailCertification(HashMap<String, Object> map);

    boolean findPasswordVerification(HashMap<String, Object> map);

    void setNewPassword(HashMap<String, Object> map);

    void removeVerification(HashMap<String, Object> map);

    void changePassword(HashMap<String, Object> map);

    boolean checkEmail(HashMap<String, Object> map);

    boolean checkId(HashMap<String, Object> map);

    void resetVerification(HashMap<String, Object> map);

    boolean findEmailCheck(HashMap<String, Object> map);

    void Withdrawal(HashMap<String, Object> map);

    boolean checkSocialUser(HashMap<String, Object> map);

    Integer getSeq(HashMap<String, Object> map);

    ArrayList<User> getList(HashMap<String, Object> map);

    Integer getListCount(HashMap<String, Object> map);

    User getDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);

    UserProfile selectProfile(HashMap<String, Object> map);

    void updateProfile(HashMap<String, Object> map);
}
