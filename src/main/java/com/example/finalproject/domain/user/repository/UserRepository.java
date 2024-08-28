package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.AuthErrorCode;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.AuthException;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmailAndType(String email, Integer type);

	Optional<User> findByName(String username);

	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);


	default User getByEmailAndType(String email, Integer type) {
		return findByEmailAndType(email, type).orElseThrow(
			() -> new AuthException(AuthErrorCode.NOT_FOUND_USER)
		);
	}

	default User getUserBySeqOrException(Integer userSeq) {
		return findById(userSeq).orElseThrow(
				() -> new ValidException(ValidErrorCode.USER_NOT_FOUND)
		);
	}

}
