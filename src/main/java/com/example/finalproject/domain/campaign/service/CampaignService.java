package com.example.finalproject.domain.campaign.service;

import com.example.finalproject.domain.campaign.dto.CampaignDto;
import com.example.finalproject.domain.campaign.repository.CampaignPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CampaignService {

    private final CampaignPreferenceRepository campaignPreferenceRepository;

    @Transactional
    public CampaignDto saveCampaignPreference(Integer campaignSeq, Integer userSeq) {
        // TODO : 사용자 존재여부 검증

        // TODO : 체험단 존재여부 검증

        // TODO : 찜 저장

        return null;
    }
}
