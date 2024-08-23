package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfluencerRepository extends JpaRepository<Influencer, Integer> {
	boolean existsByNickname(String nickname);
}
