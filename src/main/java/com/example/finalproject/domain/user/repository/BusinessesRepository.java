package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessesRepository extends JpaRepository<Businesses, Integer> {
	boolean existsByCompany(String company);

	Optional<Businesses> findByUser(User user);

	default Businesses getBusinessesByUserOrException(User user) {
		return findByUser(user).orElseThrow(
				() -> new ValidException(ValidErrorCode.USER_NOT_FOUND)
		);
	}
}