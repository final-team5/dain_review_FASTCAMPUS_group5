package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InfluencerRepository extends JpaRepository<Influencer, Integer> {
	boolean existsByNickname(String nickname);

	Optional<Influencer> findByUser(User user);

	default Influencer getInfluencerByUserOrException(User user) {
		return findByUser(user).orElseThrow(
				() -> new ValidException(ValidErrorCode.USER_NOT_FOUND)
		);
	}
}
