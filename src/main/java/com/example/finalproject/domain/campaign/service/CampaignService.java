package com.example.finalproject.domain.campaign.service;

import com.example.finalproject.domain.campaign.dto.*;
import com.example.finalproject.domain.campaign.dto.request.CampaignSearch;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignPreference;
import com.example.finalproject.domain.campaign.entity.CampaignWithApplicantCount;
import com.example.finalproject.domain.campaign.entity.enums.FirstCampaignSearchType;
import com.example.finalproject.domain.campaign.entity.enums.SecondCampaignSearchType;
import com.example.finalproject.domain.campaign.repository.CampaignPreferenceRepository;
import com.example.finalproject.domain.campaign.repository.CampaignRepository;
import com.example.finalproject.domain.campaign.specification.CampaignSpecification;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Campaign> getCampaignList(CampaignSearch search) {
        return campaignRepository.findAll(getCampaignSpecification(search));
    }

    public long getCampaignListCount(CampaignSearch search) {
        return campaignRepository.count(getCampaignSpecification(search));
    }

    private Specification<Campaign> getCampaignSpecification(CampaignSearch search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 상태가 3일 때만 모든 필터 적용
            if (search.getStatus() != null && search.getStatus() == 3) {

                // 검색어 필터
                if (search.getSearchWord() != null && !search.getSearchWord().isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("title"), search.getSearchWord()));
                }

                // 도시 필터
                if (search.getCity() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("city"), City.fromValue(search.getCity()).name()));
                }

                // 구 필터
                if (search.getDistricts() != null && !search.getDistricts().isEmpty()) {
                    predicates.add(root.get("district").in(
                        search.getDistricts().stream()
                            .map(districtValue -> District.fromValue(districtValue, City.fromValue(search.getCity()).name())) // 도시와 구 번호로 District를 가져옴
                            .map(District::getDistrictName) // 구 이름만 추출
                            .collect(Collectors.toList())
                    ));
                }

                // 카테고리 필터
                if (search.getCategorySeq() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("category"), Category.fromValue(search.getCategorySeq()).name()));
                }

                // 유형 필터
                if (search.getTypeSeq() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), Type.fromValue(search.getTypeSeq()).name()));
                }

                // SNS 플랫폼 필터
                if (search.getPlatformSeq() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("platform"), Sns.fromValue(search.getPlatformSeq()).name()));
                }

                // 프리미엄 여부 필터
                if (search.isPremium()) {
                    // tag가 null이 아니고, 현재 날짜가 신청 기간 내에 있는 경우
                    predicates.add(criteriaBuilder.isNotNull(root.get("tag")));
                    predicates.add(criteriaBuilder.between(
                        criteriaBuilder.currentDate(),
                        root.get("applicationStartDate"),
                        root.get("applicationEndDate")
                    ));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Campaign getDetail(Integer id) {
        return campaignRepository.findById(id).orElse(null);
    }

    public Page<CampaignDto> findCampaignPage(FirstCampaignSearchType searchType1, SecondCampaignSearchType searchType2, String searchWord, Pageable pageable) {

        Specification<Campaign> campaignSpecification = (root, query, criteriaBuilder) -> null;

        if (searchType2 != null) {
            campaignSpecification = campaignSpecification.and(CampaignSpecification.findByCampaignStatus(searchType2.getCode()));
        }

        if (searchWord != null && !searchWord.isEmpty()) {
            if (searchType1 == null) {
                campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInAll(searchWord));
            } else {
                switch (searchType1) {
                    case ALL:
                        campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInAll(searchWord));
                        break;
                    case CAMPAIGN_TITLE:
                        campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInTitle(searchWord));
                        break;
                    case ID:
                        campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInUserEmail(searchWord));
                        break;
                    case COMPANY_NAME:
                        campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInTitle(searchWord));
                        break;
                    case PHONE_NUMBER:
                        campaignSpecification = campaignSpecification.and(CampaignSpecification.findBySearchWordInUserPhone(searchWord));
                    default:
                        throw new ValidException(ValidErrorCode.CAMPAIGN_TYPE_INVALID);
                }
            }
        }

        return campaignRepository.findAll(campaignSpecification, pageable).map(CampaignDto::from);
    }

}
