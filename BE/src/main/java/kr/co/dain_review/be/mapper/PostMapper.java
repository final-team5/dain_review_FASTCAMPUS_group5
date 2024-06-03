package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.post.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface PostMapper {
    ArrayList<Post> select(HashMap<String, Object> map);

    Integer selectCount(HashMap<String, Object> map);

    Post selectDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);


    void deleteAdmin(HashMap<String, Object> map);

    void updateAdmin(HashMap<String, Object> map);

    void insertAdmin(HashMap<String, Object> map);

}
