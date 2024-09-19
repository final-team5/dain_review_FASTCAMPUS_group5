package com.example.finalproject.domain.alarm.repository;

import com.example.finalproject.domain.alarm.entity.Alarm;
import com.example.finalproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    Integer countByUser(User user);
}
