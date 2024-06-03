package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.PointMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.point.AdminPointUpdate;
import kr.co.dain_review.be.model.point.Point;
import kr.co.dain_review.be.model.user.AdminUserInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class PointService {
    @Autowired
    private PointMapper pointMapper;

    public ArrayList<Point> getList(Search search) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return pointMapper.selectList(map);
    }

    public Integer getListCount(Search search){
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return pointMapper.selectListCount(map);
    }


    public Point getDetail(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        return pointMapper.selectDetail(map);
    }

    public void setUpdate(AdminPointUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        pointMapper.update(map);
    }

    public void setInsert(AdminUserInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        pointMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            pointMapper.delete(map);
        }
    }
}
