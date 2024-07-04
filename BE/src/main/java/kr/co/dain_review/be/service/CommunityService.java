package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.CommunityMapper;
import kr.co.dain_review.be.model.community.CommunityCommentsInsert;
import kr.co.dain_review.be.model.community.CommunityCommentsUpdate;
import kr.co.dain_review.be.model.community.Communities;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
@Service
public class CommunityService {
    @Autowired
    private CommunityMapper communityMapper;



    public void update(PostUpdate update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("userSeq", userSeq);
        System.out.println("update : "+map);
        communityMapper.update(map);
    }

    public void insert(PostInsert insert, Integer type, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        communityMapper.insert(map);
    }

    public void delete(Delete delete, Integer userSeq) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            map.put("userSeq", userSeq);
            System.out.println("delete : "+map);
            communityMapper.delete(map);
        }
    }

    public void insertCommunityComments(CommunityCommentsInsert insert, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("userSeq", userSeq);
        communityMapper.insertCommunityComments(map);
    }

    public void updateCommunityComments(CommunityCommentsUpdate update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("userSeq", userSeq);
        communityMapper.updateCommunityComments(map);
    }

    public void deleteCommunityComments(Delete delete, Integer userSeq) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            map.put("userSeq", userSeq);
            communityMapper.deleteCommunityComments(map);
        }
    }

    public ArrayList<Communities> list(Search search, Integer type, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        return communityMapper.list(map);
    }

    public Integer count(Search search, Integer type, Integer userSeq){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        return communityMapper.count(map);
    }

    public Communities detail(Integer seq, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("type", type);
        return communityMapper.detail(map);
    }



}

