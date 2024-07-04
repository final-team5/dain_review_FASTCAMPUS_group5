package kr.co.dain_review.be.mapper;

import kr.co.dain_review.be.model.transaction.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
@Mapper
public interface TransactionMapper {


    ArrayList<Transaction> list(HashMap<String, Object> map);

    Integer count(HashMap<String, Object> map);

    void update(HashMap<String, Object> map);

    void insert(HashMap<String, Object> map);
}
