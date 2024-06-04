package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.AlarmMapper;
import kr.co.dain_review.be.mapper.ProductMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.product.*;
import kr.co.dain_review.be.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private AlarmMapper alarmMapper;

    public ArrayList<Product> getList(ProductSearch search) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        System.out.println(productMapper.selectList(map));
        return productMapper.selectList(map);
    }

    public ArrayList<Product> getList(ProductSearch search, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("userSeq", userSeq);
        return productMapper.selectList(map);
    }

    public ArrayList<Product> applicationsList(ProductSearch search, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("applicantSeq", userSeq);
        return productMapper.selectList(map);
    }

    public Integer getListCount(ProductSearch search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return productMapper.selectListCount(map);
    }

    public Integer getListCount(ProductSearch search, Integer userSeq){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        map.put("userSeq", userSeq);
        return productMapper.selectListCount(map);
    }


    public Product getDetail(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        return productMapper.selectDetail(map);
    }

    public Product getDetail(Integer seq, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("userSeq", userSeq);
        return productMapper.selectDetail(map);
    }

    public void setUpdate(ProductUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        productMapper.update(map);
        if(update.getMessage()!=null){

            alarmMapper.insert(map);
        }
    }

    public void setInsert(ProductInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        productMapper.insert(map);
    }
    public void setInsert(ProductInsert insert, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        map.put("userSeq", userSeq);
        productMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            productMapper.delete(map);
        }
    }

    public void application(InfluencerApplication application, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(application, HashMap.class);
        map.put("userSeq", userSeq);
        productMapper.application(map);
    }

    public void cancellation(Integer seq, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("userSeq", userSeq);
        productMapper.cancellation(map);
    }

    public ArrayList<Product> getFavoriteList(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return productMapper.selectFavoriteList(map);
    }

    public Integer getFavoriteListCount(Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        return productMapper.selectFavoriteListCount(map);
    }

    public void setProgress(Integer seq, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        map.put("userSeq", userSeq);
        productMapper.progress(map);
    }

    public void InfluencerSelect(InfluencerSelect select) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(select, HashMap.class);
        productMapper.InfluencerSelect(map);
    }

    public boolean myProductCheck(Integer userSeq, Integer productSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userSeq", userSeq);
        map.put("productSeq", productSeq);
        return productMapper.myProductCheck(map);
    }

    public ArrayList<ProductInfluencer> applicationInfluencer(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("productSeq", seq);
        map.put("platformType", productMapper.getProductPlatformType(map));
        return productMapper.applicationInfluencer(map);
    }

    public ArrayList<ProductInfluencer> selectionInfluencer(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("productSeq", seq);
        map.put("platformType", productMapper.getProductPlatformType(map));
        return productMapper.selectionInfluencer(map);
    }


    public void review(ReportInsert insert, Integer userSeq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("productSeq", insert.getProductSeq());
        map.put("link", insert.getLink());
        map.put("userSeq", userSeq);
        if(insert.getAttachments()!=null){
            String filename = FileUtils.setNewName(insert.getAttachments());
            map.put("attachments", filename);
            FileUtils.saveFile(insert.getAttachments(), "attachments/"+filename);
        }
        productMapper.report(map);
    }
}
