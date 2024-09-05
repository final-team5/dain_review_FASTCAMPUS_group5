package com.example.finalproject.domain.campaign.service;

import com.example.finalproject.domain.campaign.dto.CampaignPreferenceDto;
import com.example.finalproject.domain.campaign.dto.CampaignWithApplicantCountDto;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount;
import com.example.finalproject.domain.campaign.repository.CampaignPreferenceRepository;
import com.example.finalproject.domain.campaign.repository.CampaignRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
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
     * @param userEmail : 로그인한 사용자 ID
     * @return : CampaignPreferenceDto
     */
    @Transactional
    public CampaignPreferenceDto saveCampaignPreference(Integer campaignSeq, String userEmail) {
        User user = userRepository.getUserByEmailOrException(userEmail);

        Campaign campaign = campaignRepository.getCampaignBySeqOrException(campaignSeq);

        validateAlreadySavedPreference(user, campaign);

        CampaignPreference campaignPreference = CampaignPreference.of(user, campaign);
        CampaignPreference savedCampaignPreference = campaignPreferenceRepository.save(campaignPreference);

        return CampaignPreferenceDto.from(savedCampaignPreference);
    }

    /**
     * 체험단 찜 제거 기능.
     *
     * @param campaignSeq : 찜을 취소할 체험단 ID
     * @param userEmail : 로그인한 사용자 ID
     */
    @Transactional
    public void deleteCampaignPreference(Integer campaignSeq, String userEmail) {
        User user = userRepository.getUserByEmailOrException(userEmail);
        Campaign campaign = campaignRepository.getCampaignBySeqOrException(campaignSeq);

        CampaignPreference campaignPreference = campaignPreferenceRepository.getCampaignPreferenceByCampaignOrException(campaign);

        validateCampaignPreferenceUserMatch(user.getSeq(), campaignPreference);

        campaignPreferenceRepository.deleteById(campaignPreference.getSeq());
    }

    /**
     * 사용자 본인이 찜한 내역인지 판단 기능.
     *
     * @param userSeq : 사용자 ID
     * @param campaignPreference : 찜 내역 ID
     */
    private static void validateCampaignPreferenceUserMatch(Integer userSeq, CampaignPreference campaignPreference) {
        if (!campaignPreference.getUser().getSeq().equals(userSeq)) {
            throw new ValidException(ValidErrorCode.CAMPAIGN_PREFERENCE_USER_MISMATCH);
        }
    }

    /**
     * 이미 찜을 누른 체험단인지 판단 기능.
     *
     * @param user : 사용자 정보
     * @param campaign : 체험단 정보
     */
    private void validateAlreadySavedPreference(User user, Campaign campaign) {
        if (campaignPreferenceRepository.existsByCampaignAndUser(campaign, user)) {
            throw new ValidException(ValidErrorCode.CAMPAIGN_PREFERENCE_ALREADY_SAVED);
        }
    }

    /**
     * 찜 목록 전체 조회 리스트 기능.
     *
     * @param userEmail : 로그인한 사용자 ID
     * @param pageable : 페이징 인자
     * @return Page<CampaignWithApplicantCountDto>
     */
    public Page<CampaignWithApplicantCountDto> getCampaignPreferenceList(String userEmail, Pageable pageable) {
        User user = userRepository.getUserByEmailOrException(userEmail);

        Page<CampaignWithApplicantCount> campaignWithApplicantCounts = campaignRepository.findAllByUserAndStatus(user, 3, pageable);

        return campaignWithApplicantCounts.map(CampaignWithApplicantCountDto::from);
    }
}
