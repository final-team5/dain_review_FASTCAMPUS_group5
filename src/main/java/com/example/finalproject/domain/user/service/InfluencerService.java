package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.user.dto.response.InfluencerDetailResponse;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfluencerService {

    private final InfluencerRepository influencerRepository;

    @Autowired
    public InfluencerService(InfluencerRepository influencerRepository) {
        this.influencerRepository = influencerRepository;
    }

    /**
     * 캠페인에 연결된 모든 인플루언서의 상세 정보를 조회하여 반환합니다.
     *
     * @param campaignSeq 캠페인 시퀀스
     * @return 캠페인에 연결된 인플루언서의 상세 정보 목록
     */
    public List<InfluencerDetailResponse> getInfluencersForCampaign(Long campaignSeq) {
        List<Influencer> influencers = influencerRepository.findByCampaignSeq(campaignSeq);
        return influencers.stream()
                .map(this::convertToInfluencerDetailResponse)
                .collect(Collectors.toList());
    }

    private InfluencerDetailResponse convertToInfluencerDetailResponse(Influencer influencer) {
        // TODO: 상세 구현 필요
        String designatedPlatform = "Instagram";

        return new InfluencerDetailResponse(
                influencer.getSeq(),
                influencer.getUser().getName(),
                1,
                designatedPlatform,
                influencer
        );
    }
}