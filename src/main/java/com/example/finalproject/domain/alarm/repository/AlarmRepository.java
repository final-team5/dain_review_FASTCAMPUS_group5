package com.example.finalproject.domain.alarm.repository;

import com.example.finalproject.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

}