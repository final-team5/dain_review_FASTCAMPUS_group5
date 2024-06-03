package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.AlarmMapper;
import kr.co.dain_review.be.mapper.ProductMapper;
import kr.co.dain_review.be.model.alarm.AdminAlarmInsert;
import kr.co.dain_review.be.model.alarm.AdminAlarmUpdate;
import kr.co.dain_review.be.model.alarm.Alarm;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.product.ProductUpdate;
import kr.co.dain_review.be.model.product.productApplicants;
import kr.co.dain_review.be.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
@Service
public class AlarmService {
    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private ProductMapper productMapper;

    public ArrayList<Alarm> getList(Search search) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return alarmMapper.selectList(map);
    }

    public Integer getListCount(Search search){
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return alarmMapper.selectListCount(map);
    }


    public void setUpdate(AdminAlarmUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        alarmMapper.update(map);
    }

    public void setInsert(AdminAlarmInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        alarmMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            alarmMapper.delete(map);
        }
    }

    public ArrayList<Alarm> getAlarmList(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return alarmMapper.selectList(map);
    }

    public Integer getAlarmListCount(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return alarmMapper.selectListCount(map);
    }

    public void insert(ProductUpdate update) {
    }

    public void productProgress(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("productSeq", seq);
        String productTitle = productMapper.getProductTitle(map);
        ArrayList<productApplicants> applicationInfluencerList = productMapper.applicationInfluencerSeq(map);
        for(productApplicants pa : applicationInfluencerList){
            map.put("userSeq", pa.getUserSeq());
            if (pa.getApplication()){
                map.put("message", "신청하신 "+productTitle+" 체험단에 선정되었습니다. 광고주와 연락하여 체험/리뷰를 진행해주세요.");
            }
            else {
                map.put("message", "신청하신 "+productTitle+" 체험단에 선정되지 않았습니다.");
            }
            alarmMapper.insert(map);
        }
    }
}
