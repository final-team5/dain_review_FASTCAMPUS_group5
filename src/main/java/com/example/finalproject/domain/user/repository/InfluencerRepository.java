package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfluencerRepository extends JpaRepository<Influencer, Integer> {
	boolean existsByNickname(String nickname);

	boolean existsByUser(User user);
}
