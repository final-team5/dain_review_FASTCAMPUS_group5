package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.ReportMapper;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.report.AdminReportInsert;
import kr.co.dain_review.be.model.report.AdminReportUpdate;
import kr.co.dain_review.be.model.report.Report;
import kr.co.dain_review.be.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public ArrayList<Report> getList(Search search) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return reportMapper.selectList(map);
    }

    public Integer getListCount(Search search){
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", search.getPage());
        map.put("searchWord", search.getSearchWord());
        map.put("searchType", search.getSearchType());
        return reportMapper.selectListCount(map);
    }


    public Report getDetail(Integer seq) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", seq);
        return reportMapper.selectDetail(map);
    }

    public void setUpdate(AdminReportUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        reportMapper.update(map);
    }

    public void setInsert(AdminReportInsert insert) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(insert, HashMap.class);
        reportMapper.insert(map);
    }

    public void setDelete(Delete delete) {
        for(Integer seq : delete.getSeqs()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("seq", seq);
            reportMapper.delete(map);
        }
    }
}
