package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.post.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface PostMapper {

    ArrayList<Post> list(HashMap<String, Object> map);

    Integer count(HashMap<String, Object> map);

    Post detail(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);
}
