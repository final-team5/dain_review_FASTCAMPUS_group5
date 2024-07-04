package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.user.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface UserMapper {

    User getUser(HashMap<String, Object> map);

    boolean checkNickname(HashMap<String, Object> map);

    boolean checkCompany(HashMap<String, Object> map);

    boolean checkAuthentication(HashMap<String, Object> map);

    boolean checkPhone(HashMap<String, Object> map);


    String findEmail(HashMap<String, Object> map);

    void insertVerification(HashMap<String, Object> map);

    Integer getAuthenticationNumber(HashMap<String, Object> map);

    String findPassword(HashMap<String, Object> map);

    boolean getFindUser(HashMap<String, Object> map);

    void userCertification(HashMap<String, Object> map);

    void emailCertification(HashMap<String, Object> map);

    boolean findPasswordVerification(HashMap<String, Object> map);

    void setNewPassword(HashMap<String, Object> map);

    void deleteVerification(HashMap<String, Object> map);

    void changePassword(HashMap<String, Object> map);

    boolean checkEmail(HashMap<String, Object> map);

    boolean checkId(HashMap<String, Object> map);

    boolean findEmailCheck(HashMap<String, Object> map);

    void Withdrawal(HashMap<String, Object> map);

    boolean checkSocialUser(HashMap<String, Object> map);

    Integer getSeq(HashMap<String, Object> map);

    ArrayList<User> getList(HashMap<String, Object> map);

    Integer getListCount(HashMap<String, Object> map);

    User getDetail(HashMap<String, Object> map);

    void insertUser(HashMap<String, Object> map);

    void deleteUser(HashMap<String, Object> map);

    UserProfile selectProfile(HashMap<String, Object> map);

    void updateUser(HashMap<String, Object> map);

    BusinessesDetail getBusinessesDetail(HashMap<String, Object> map);

    InfluencerDetail getInfluencerDetail(HashMap<String, Object> map);

    User getUserDetail(HashMap<String, Object> map);

    void updateInfluencer(HashMap<String, Object> map);

    void updateBusinesses(HashMap<String, Object> map);

    void insertInfluencer(HashMap<String, Object> map);

    void insertBusinesses(HashMap<String, Object> map);

    Integer getUserSeq(HashMap<String, Object> map);

    UserProfile getUserProfile(HashMap<String, Object> map);

    InfluencerProfile getInfluencerProfile(HashMap<String, Object> map);

    BusinessesProfile getBusinessesProfile(HashMap<String, Object> map);

    void agencyApplication(HashMap<String, Object> map);
}
