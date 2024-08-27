package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.User;

import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmailAndType(String email, Integer type);

	default User getByEmailAndType(String email, Integer type) {
		return findByEmailAndType(email, type).orElseThrow(
			() -> new AuthException(AuthErrorCode.NOT_FOUND_USER)
		);
	}

	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);
}