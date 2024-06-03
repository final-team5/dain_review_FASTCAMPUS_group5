package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.report.Report;
import kr.co.dain_review.be.model.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface ReportMapper {
    ArrayList<Report> selectList(HashMap<String, Object> map);

    Integer selectListCount(HashMap<String, Object> map);

    Report selectDetail(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);

    void delete(HashMap<String, Object> map);
}
