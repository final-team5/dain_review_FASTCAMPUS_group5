package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Enterpriser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriserRepository extends JpaRepository<Enterpriser, Integer> {
	boolean existsByCompany(String company);
}
