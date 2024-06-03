package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.product.Product;
import kr.co.dain_review.be.model.product.ProductInfluencer;
import kr.co.dain_review.be.model.product.ProductSchedule;
import kr.co.dain_review.be.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface ProductMapper {
    ArrayList<Product> selectList(HashMap<String, Object> map);

    Integer selectListCount(HashMap<String, Object> map);

    Product selectDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);

    void application(HashMap<String, Object> map);

    void cancellation(HashMap<String, Object> map);

    ArrayList<Product> selectFavoriteList(HashMap<String, Object> map);

    Integer selectFavoriteListCount(HashMap<String, Object> map);

    void progress(HashMap<String, Object> map);

    void InfluencerSelect(HashMap<String, Object> map);

    boolean myProductCheck(HashMap<String, Object> map);

    ArrayList<ProductInfluencer> applicationInfluencer(HashMap<String, Object> map);

    ArrayList<ProductInfluencer> selectionInfluencer(HashMap<String, Object> map);
}
