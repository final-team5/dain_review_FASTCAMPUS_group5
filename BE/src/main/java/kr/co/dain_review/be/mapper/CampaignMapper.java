package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.campaign.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface CampaignMapper {
    ArrayList<Campaign> selectList(HashMap<String, Object> map);

    Integer selectListCount(HashMap<String, Object> map);

    Campaign selectDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);

    void application(HashMap<String, Object> map);

    void cancellation(HashMap<String, Object> map);

    ArrayList<Campaign> selectFavoriteList(HashMap<String, Object> map);

    Integer selectFavoriteListCount(HashMap<String, Object> map);

    void progress(HashMap<String, Object> map);

    void InfluencerSelect(HashMap<String, Object> map);

    boolean myCampaignCheck(HashMap<String, Object> map);

    ArrayList<CampaignInfluencer> applicationInfluencer(HashMap<String, Object> map);

    ArrayList<CampaignInfluencer> selectionInfluencer(HashMap<String, Object> map);

//    ArrayList<campaignApplicants> selectedInfluencer(HashMap<String, Object> map);

    ArrayList<CampaignApplicants> applicationInfluencerSeq(HashMap<String, Object> map);

    String getCampaignTitle(HashMap<String, Object> map);

    void report(HashMap<String, Object> map);

    Integer getCampaignUserSeq(HashMap<String, Object> map);

    ArrayList<Mission> missions();

    void updateMission(HashMap<String, Object> map);

    void deleteMission(HashMap<String, Object> map);

    void insertMission(HashMap<String, Object> map);

    ArrayList<Summary> getSummary(HashMap<String, Object> map);

    Integer getCampaignSeq(HashMap<String, Object> map);

    boolean getKeywordView(HashMap<String, Object> map);

}
