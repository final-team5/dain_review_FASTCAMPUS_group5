package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.User;

import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmailAndLoginType(String email, Integer type);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

	default User getByEmailAndLoginType(String email, Integer type) {
		return findByEmailAndLoginType(email, type).orElseThrow(
			() -> new AuthException(AuthErrorCode.NOT_FOUND_USER)
		);
	}

	Optional<User> findByName(String name);
}
