package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shapesecurity.salvation2.Values.Hash;
import kr.co.dain_review.be.mapper.AlarmMapper;
import kr.co.dain_review.be.mapper.CampaignMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

import static kr.co.dain_review.be.config.WebMVCConfig.filePath;

@Service
public class CampaignService {
    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private AlarmMapper alarmMapper;

    public ArrayList<Campaign> getList(CampaignSearch search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        System.out.println(campaignMapper.selectList(map));
        return campaignMapper.selectList(map);
    }

    public ArrayList<Campaign> getList(CampaignSearch search, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("userSeq", userSeq);
        return campaignMapper.selectList(map);
    }

    public ArrayList<Campaign> applicationsList(CampaignSearch search, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("applicantSeq", userSeq);
        return campaignMapper.selectList(map);
    }

    public Integer getListCount(CampaignSearch search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.selectListCount(map);
    }

    public Integer getListCount(CampaignSearch search, Integer userSeq){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("userSeq", userSeq);
        return campaignMapper.selectListCount(map);
    }


    public Campaign getDetail(String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return campaignMapper.selectDetail(map);
    }

    public Campaign getDetail(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("userSeq", userSeq);
        return campaignMapper.selectDetail(map);
    }

    public void setUpdate(CampaignUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        campaignMapper.update(map);
        if(update.getMessage()!=null){

            alarmMapper.insert(map);
        }
    }

    public void setInsert(String id, CampaignInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("id", id);
        campaignMapper.insert(map);
    }
    public void setInsert(String id, CampaignInsert insert, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("id", id);
        map.put("userSeq", userSeq);
        campaignMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            campaignMapper.delete(map);
        }
    }

    public void application(InfluencerApplication application, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(application, HashMap.class);
        map.put("userSeq", userSeq);
        campaignMapper.application(map);
    }

    public void cancellation(String campaignId, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", campaignId);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        campaignMapper.cancellation(map);
    }

    public ArrayList<Campaign> getFavoriteList(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return campaignMapper.selectFavoriteList(map);
    }

    public Integer getFavoriteListCount(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return campaignMapper.selectFavoriteListCount(map);
    }

    public void setProgress(Integer seq, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("userSeq", userSeq);
        campaignMapper.progress(map);
    }

    public void InfluencerSelect(InfluencerSelect select) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(select, HashMap.class);
        campaignMapper.InfluencerSelect(map);
    }

    public boolean myCampaignCheck(Integer userSeq, Integer campaignSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("campaignSeq", campaignSeq);
        return campaignMapper.myCampaignCheck(map);
    }

    public ArrayList<CampaignInfluencer> applicationInfluencer(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignSeq", seq);
        return campaignMapper.applicationInfluencer(map);
    }

    public ArrayList<CampaignInfluencer> selectionInfluencer(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignSeq", seq);
        return campaignMapper.selectionInfluencer(map);
    }


    public void review(ReportInsert insert, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", insert.getCampaignId());
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("link", insert.getLink());
        map.put("userSeq", userSeq);
        if(insert.getAttachments()!=null){
            String filename = FileUtils.setNewName(insert.getAttachments());
            map.put("attachments", filename);
            FileUtils.saveFile(insert.getAttachments(), "attachments/"+filename);
        }
        campaignMapper.report(map);
    }

    public ArrayList<Mission> missions() {
        return campaignMapper.missions();
    }

    public void updateMission(MissionUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        campaignMapper.updateMission(map);
    }

    public void deleteMission(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            campaignMapper.deleteMission(map);
        }
    }

    public void insertMission(MissionInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        campaignMapper.insertMission(map);
    }


    public boolean checkApplication(Integer campaignSeq, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignSeq", campaignSeq);
        map.put("userSeq", userSeq);
        return campaignMapper.checkApplication(map);
    }

    public void insertFavorites(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        campaignMapper.insertFavorites(map);
    }

    public void deleteFavorites(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        campaignMapper.deleteFavorites(map);
    }

    public ArrayList<CampaignComment> CampaignComments(Integer campaignSeq){
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignSeq", campaignSeq);
        return campaignMapper.CampaignComments(map);
    }

    public void insertCampaignComments(CampaignCommentsInsert insert, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("userSeq", userSeq);
        campaignMapper.insertComments(map);
    }

    public void updateCampaignComments(CampaignCommentsUpdate update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("userSeq", userSeq);
        campaignMapper.updateComments(map);
    }

    public void deleteCampaignComments(Delete delete, Integer userSeq) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            map.put("userSeq", userSeq);
            campaignMapper.deleteComments(map);
        }
    }

    public void cancellation2(Cancel cancel, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", cancel.getCampaignId());
        map.put("campaignSeq", campaignMapper.getCampaignUserSeq(map));
        map.put("userSeq", userSeq);
        map.put("reason", cancel.getReason());
        map.put("type", cancel.getType());
        if(cancel.getEvidentiary1()!=null){
            String fileName = FileUtils.setNewName(cancel.getEvidentiary1());
            FileUtils.saveFile(cancel.getEvidentiary1(), filePath +"evidentiary/"+fileName);
            map.put("evidentiary1", fileName);
        }
        if(cancel.getEvidentiary2()!=null){
            String fileName = FileUtils.setNewName(cancel.getEvidentiary2());
            FileUtils.saveFile(cancel.getEvidentiary2(), filePath +"evidentiary/"+fileName);
            map.put("evidentiary2", fileName);
        }
        if(cancel.getEvidentiary3()!=null){
            String fileName = FileUtils.setNewName(cancel.getEvidentiary3());
            FileUtils.saveFile(cancel.getEvidentiary3(), filePath +"evidentiary/"+fileName);
            map.put("evidentiary3", fileName);
        }
        campaignMapper.cancel(map);
    }

    public ArrayList<Campaign> influencerCampaign(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return campaignMapper.influencerCampaign(map);
    }

    public ArrayList<Cancel> cancelList(Search search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.cancelList(map);
    }

    public Integer cancelCount(Search search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.cancelCount(map);
    }

    public boolean cancelRequestCheck(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        return campaignMapper.cancelRequestCheck(map);
    }
}
