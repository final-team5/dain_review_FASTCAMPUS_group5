package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessesRepository extends JpaRepository<Businesses, Integer> {
	boolean existsByCompany(String company);

	Optional<Businesses> findByUser(User user);
}