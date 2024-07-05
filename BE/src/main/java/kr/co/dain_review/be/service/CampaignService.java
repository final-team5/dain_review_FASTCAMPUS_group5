package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.AlarmMapper;
import kr.co.dain_review.be.mapper.CampaignMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.campaign.*;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.util.ExcelGenerator;
import kr.co.dain_review.be.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static kr.co.dain_review.be.config.WebMVCConfig.filePath;

@Service
public class CampaignService {
    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private AlarmMapper alarmMapper;

    public ArrayList<Campaign> getList(Search search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.selectList(map);
    }

    public Integer getListCount(Search search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
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
        map.put("message", insert.getTitle()+" 체험단을 신청하였습니다. 검수 승인 시 체험단이 등록됩니다.");
        map.put("targetSeq", userSeq);
        map.put("targetType", 2);
        alarmMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("campaignSeq", seq);
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
        map.put("campaignId", campaignId);
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

    public void influencerSelect(InfluencerSelect select) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(select, HashMap.class);
        campaignMapper.influencerSelect(map);
    }

    public boolean myCampaignCheck(Integer userSeq, String campaignId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", campaignId);
        map.put("userSeq", userSeq);
        return campaignMapper.myCampaignCheck(map);
    }

    public ArrayList<CampaignInfluencer> applicationInfluencer(String campaignId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", campaignId);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        return campaignMapper.applicationInfluencer(map);
    }

    public ArrayList<CampaignInfluencer> selectionInfluencer(String campaignId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", campaignId);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        return campaignMapper.selectionInfluencer(map);
    }


    public void review(ReportInsert insert, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", insert.getCampaignId());
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
        map.put("campaignId", id);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        campaignMapper.insertFavorites(map);
    }

    public void deleteFavorites(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", id);
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

    public void cancellation2(CancelInsert cancel, Integer userSeq) {
        if(cancel.getType()==1) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("campaignId", cancel.getCampaignId());
            map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
            map.put("userSeq", userSeq);
            map.put("reason", cancel.getReason());
            map.put("type", cancel.getType());
            if (cancel.getEvidentiary1() != null) {
                String fileName = FileUtils.setNewName(cancel.getEvidentiary1());
                FileUtils.saveFile(cancel.getEvidentiary1(), filePath + "evidentiary/" + fileName);
                map.put("evidentiary1", fileName);
            }
            if (cancel.getEvidentiary2() != null) {
                String fileName = FileUtils.setNewName(cancel.getEvidentiary2());
                FileUtils.saveFile(cancel.getEvidentiary2(), filePath + "evidentiary/" + fileName);
                map.put("evidentiary2", fileName);
            }
            if (cancel.getEvidentiary3() != null) {
                String fileName = FileUtils.setNewName(cancel.getEvidentiary3());
                FileUtils.saveFile(cancel.getEvidentiary3(), filePath + "evidentiary/" + fileName);
                map.put("evidentiary3", fileName);
            }
            campaignMapper.cancel(map);
        }
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
        map.put("campaignId", id);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("userSeq", userSeq);
        return campaignMapper.cancelRequestCheck(map);
    }

    public Cancel cancelDetail(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        return campaignMapper.getCampaignCancelRequest(map);
    }

    public void campaignCancel(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("userSeq", userSeq);
        campaignMapper.campaignCancel(map);
    }

    public Boolean isCampaignDelete(String id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return campaignMapper.isCampaignDelete(map);
    }

    public void deleteCampaign(String id, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("userSeq", userSeq);
        campaignMapper.deleteCampaign(map);
    }

    public void campaignSimpleCancellation(CancelInsert cancel, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        if(cancel.getType()==2) {
            if (cancel.getIsHost() == 1) {

            } else if (cancel.getIsHost() == 0){

            }
        }

    }

    public void campaignApproval(CampaignApproval approval) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(approval, HashMap.class);
        Integer campaignSeq = campaignMapper.getCampaignSeq(map);
        map.put("campaignSeq", campaignSeq);
        Integer userSeq = campaignMapper.getCampaignUserSeq(map);
        String title = campaignMapper.getCampaignTitle(map);

        if(approval.getApproval()==1){
            map.put("status", 2);
            campaignMapper.update(map);
            map.put("targetSeq", campaignSeq);
            map.put("targetType", 1);
            map.put("message", title + "체험단이 승인되었습니다.");
            alarmMapper.insert(map);
        } else if(approval.getApproval()==0){
            campaignMapper.delete(map);
            map.put("targetSeq", userSeq);
            map.put("targetType", 2);
            map.put("message", title + "체험단이 반려되었습니다. \n반려 사유 : "+approval.getReason());
            alarmMapper.insert(map);
        }

    }

    public ArrayList<Campaign> publicList(CampaignSearch search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.selectPublicList(map);
    }

    public Integer publicCount(CampaignSearch search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return campaignMapper.selectPublicListCount(map);
    }

    public byte[] getExcel(String id) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("campaignId", id);
            Integer campaignSeq = campaignMapper.getCampaignSeq(map);
            map.put("campaignSeq", campaignSeq);

            CampaignDate period = campaignMapper.getCampaignPeriod(map);
            ArrayList<String> participants = campaignMapper.getCampaignParticipants(map);

            return ExcelGenerator.generateCampaignExcel(period, participants);
        } catch (Exception e) {
            e.printStackTrace(); // 실제 애플리케이션에서는 로깅을 통해 예외를 기록하는 것이 좋습니다.
            return null;
        }
    }

    public void excelUpload(CampaignExcelUpload upload) throws IOException {
        ArrayList<CampaignExcel> list = ExcelGenerator.readExcelFile(upload.getExcel().getInputStream());
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", upload.getId());
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        for(CampaignExcel excel : list){
            map.put("date", excel.getDate());
            map.put("nickname", excel.getNickname());
            map.put("pcView", excel.getPcViews());
            map.put("mobileView", excel.getMobileViews());
            campaignMapper.insertCampaignPerformance(map);
        }
    }

    public Boolean campaignRecruiterCheck(InfluencerSelect select) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", select.getCampaignId());
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        return campaignMapper.campaignRecruiterCheck(map);
    }


    public void completeRecruitment(String campaignId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("campaignId", campaignId);
        map.put("campaignSeq", campaignMapper.getCampaignSeq(map));
        map.put("status", 4);
        campaignMapper.update(map);

        String campaignTitle = campaignMapper.getCampaignTitle(map);
        ArrayList<CampaignApplicants> applicationInfluencerList = campaignMapper.applicationInfluencerSeq(map);
        for(CampaignApplicants pa : applicationInfluencerList){
            map.put("userSeq", pa.getUserSeq());
            map.put("targetSeq", pa.getUserSeq());
            map.put("targetType", 2);
            if (pa.getApplication()){
                map.put("message", "신청하신 "+campaignTitle+" 체험단에 선정되었습니다. 광고주와 연락하여 체험/리뷰를 진행해주세요.");
            }
            else {
                map.put("message", "신청하신 "+campaignTitle+" 체험단에 선정되지 않았습니다.");
            }
            alarmMapper.insert(map);
        }

    }
}
