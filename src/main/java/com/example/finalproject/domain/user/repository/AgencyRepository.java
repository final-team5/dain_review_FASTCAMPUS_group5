package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {
}