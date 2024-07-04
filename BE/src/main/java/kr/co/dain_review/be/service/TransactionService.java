package kr.co.dain_review.be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.dain_review.be.mapper.TransactionMapper;
import kr.co.dain_review.be.mapper.PointMapper;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.transaction.AccountInfo;
import kr.co.dain_review.be.model.transaction.Payment;
import kr.co.dain_review.be.model.transaction.Transaction;
import kr.co.dain_review.be.model.transaction.TransactionUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    PointMapper pointMapper;

    @Autowired
    TransactionMapper transactionMapper;


    public ArrayList<Transaction> list (Search search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return transactionMapper.list(map);
    }

    public Integer count(Search search){
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(search, HashMap.class);
        return transactionMapper.count(map);
    }

    public void update(TransactionUpdate update) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(update, HashMap.class);
        transactionMapper.update(map);
    }

    public void withdrawals(AccountInfo info, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(info, HashMap.class);
        map.put("id", String.valueOf(UUID.randomUUID()));
        map.put("userSeq", userSeq);
        Integer point = info.getAmount() * -1;
        map.put("point", point);
        map.put("type", 4);
        map.put("description", "포인트 출금");
        pointMapper.insert(map);
        transactionMapper.insert(map);
    }

    public void deposits(Payment info, Integer userSeq) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(info, HashMap.class);
        map.put("id", String.valueOf(UUID.randomUUID()));
        map.put("userSeq", userSeq);
        Double point = (info.getAmount() / 1.2) / 1.1;
        map.put("point", point);
        map.put("type", 1);
        map.put("description", "포인트 충전");

        pointMapper.insert(map);
        transactionMapper.insert(map);
    }
}
