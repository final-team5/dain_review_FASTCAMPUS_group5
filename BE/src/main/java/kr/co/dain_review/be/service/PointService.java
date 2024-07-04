package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.PointMapper;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.point.Point;
import kr.co.dain_review.be.model.point.PointInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class PointService {
    @Autowired
    private PointMapper pointMapper;

    public ArrayList<Point> list(Search search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return pointMapper.selectList(map);
    }

    public Integer count(Search search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return pointMapper.selectListCount(map);
    }

    public void insert(PointInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        String id = String.valueOf(UUID.randomUUID());
        map.put("id", id);
        map.put("type", 6);
        pointMapper.insert(map);
    }
}
