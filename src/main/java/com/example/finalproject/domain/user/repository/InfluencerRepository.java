package com.example.finalproject.domain.user.repository;

import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InfluencerRepository extends JpaRepository<Influencer, Integer> {
	boolean existsByNickname(String nickname);

	void deleteByUser(User user);

	Optional<Influencer> findByUser(User user);

	@Query("SELECT i FROM Influencer i JOIN i.user u JOIN CampaignApplicants ca ON ca.user = u WHERE ca.campaign.seq = :campaignSeq")
	List<Influencer> findByCampaignSeq(@Param("campaignSeq") Long campaignSeq);

	default Influencer getInfluencerByUserOrException(User user) {
		return findByUser(user).orElseThrow(
				() -> new ValidException(ValidErrorCode.USER_NOT_FOUND)
		);
	}

	@Query("SELECT ca.application FROM CampaignApplicants ca WHERE ca.user.seq = :userSeq ORDER BY ca.seq DESC")
	Optional<Integer> findLatestApplicationByUserSeq(@Param("userSeq") Integer userSeq);
}
