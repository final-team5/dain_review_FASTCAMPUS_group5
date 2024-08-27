package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Businesses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessesRepository extends JpaRepository<Businesses, Integer> {
	boolean existsByCompany(String company);
}
