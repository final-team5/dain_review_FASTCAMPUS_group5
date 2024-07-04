package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.community.Communities;
import kr.co.dain_review.be.model.post.Post;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface CommunityMapper {
    ArrayList<Post> select(HashMap<String, Object> map);

    Integer selectCount(HashMap<String, Object> map);

    Post selectDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);


    void deleteAdmin(HashMap<String, Object> map);

    void updateAdmin(HashMap<String, Object> map);

    void insertAdmin(HashMap<String, Object> map);

    void countVisitor(HashMap<String, Object> map);

    ArrayList<Communities> list(HashMap<String, Object> map);

    Integer count(HashMap<String, Object> map);

    Communities detail(HashMap<String, Object> map);

    void deleteCommunityComments(HashMap<String, Object> map);

    void updateCommunityComments(HashMap<String, Object> map);

    void insertCommunityComments(HashMap<String, Object> map);
}
