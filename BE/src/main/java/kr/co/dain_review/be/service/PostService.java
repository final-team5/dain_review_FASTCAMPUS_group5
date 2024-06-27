package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.PostMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    public ArrayList<Post> select(Search search, Integer type) {
        return select(search, type, null); // userSeq 없이 호출하면 자동으로 null을 사용
    }

    public ArrayList<Post> select(Search search, Integer type, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        return postMapper.select(map);
    }

    public ArrayList<Post> selectCount(Search search, Integer type) {
        return select(search, type, null); // userSeq 없이 호출하면 자동으로 null을 사용
    }

    public Integer selectCount(Search search, Integer type, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        return postMapper.selectCount(map);
    }

    public Post selectDetail(Integer seq, Integer type) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("type", type);
        postMapper.countVisitor(map);
        return postMapper.selectDetail(map);
    }

    public void update(PostUpdate update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("userSeq", userSeq);
        postMapper.update(map);
    }

    public void insert(PostInsert insert, Integer type, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("type", type);
        map.put("userSeq", userSeq);
        postMapper.insert(map);
    }

    public void delete(Delete delete, Integer userSeq) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            map.put("userSeq", userSeq);
            postMapper.delete(map);
        }
    }

    public void deleteAdmin(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            postMapper.deleteAdmin(map);
        }
    }

    public void updateAdmin(AdminPostUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        postMapper.updateAdmin(map);
    }

    public void insertAdmin(AdminPostInsert insert) {
        ObjectMapper objectMapper =  new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        postMapper.insertAdmin(map);

    }

    public void insertCampaign(PostInsertCampaign insert, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("userSeq", userSeq);
        postMapper.insertCampaign(map);
    }

    public void updateCampaign(PostUpdateCampaign update, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        map.put("userSeq", userSeq);
        postMapper.updateCampaign(map);
    }

    public void deleteCampaign(Delete delete, Integer userSeq) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            map.put("userSeq", userSeq);
            postMapper.deleteCampaign(map);
        }
    }
}
