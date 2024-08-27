package com.example.finalproject.domain.campaign.service;

import com.example.finalproject.domain.campaign.dto.CampaignPreferenceDto;
import com.example.finalproject.domain.campaign.dto.CampaignWithApplicantCountDto;
import com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import com.example.finalproject.domain.campaign.repository.CampaignPreferenceRepository;
import com.example.finalproject.domain.campaign.repository.CampaignRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CampaignService {

    private final CampaignPreferenceRepository campaignPreferenceRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;

    /**
     * 체험단 찜 하기 기능.
     *
     * @param campaignSeq : 찜 할 체험단 ID
     * @param userSeq : 로그인한 사용자 ID
     * @return : CampaignPreferenceDto
     */
    @Transactional
    public CampaignPreferenceDto saveCampaignPreference(Integer campaignSeq, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        Campaign campaign = campaignRepository.getCampaignBySeqOrException(campaignSeq);

        CampaignPreference campaignPreference = CampaignPreference.of(user, campaign);
        CampaignPreference savedCampaignPreference = campaignPreferenceRepository.save(campaignPreference);

        return CampaignPreferenceDto.from(savedCampaignPreference);
    }

    public Page<CampaignWithApplicantCountDto> getCampaignPreferenceList(Integer userSeq, Pageable pageable) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        Page<CampaignWithApplicantCount> campaignWithApplicantCounts = campaignRepository.findAllByUserAndStatus(user, 3, pageable);

        return campaignWithApplicantCounts.map(CampaignWithApplicantCountDto::from);
    }
}
