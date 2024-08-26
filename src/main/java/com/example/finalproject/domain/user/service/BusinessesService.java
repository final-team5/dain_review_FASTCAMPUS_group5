package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.campaign.dto.request.CampaignInsertRequest;
import com.example.finalproject.domain.campaign.dto.request.ReviewerSelectRequest;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignStatus;
import com.example.finalproject.domain.campaign.repository.CampaignRepository;
import com.example.finalproject.domain.payment.dto.request.PaymentRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostUpdateRequest;
import com.example.finalproject.domain.user.dto.request.AgencyInsertRequest;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessesService {

    private CampaignRepository campaignRepository;
    private UserRepository userRepository;

    /**
     * 대행사 신청을 처리하는 메소드
     * @param insert 대행사 신청 정보
     * @param userSeq 사용자 식별자
     * @return 처리된 대행사 신청 정보
     */
    public AgencyInsertRequest agencyApplication(AgencyInsertRequest insert, Integer userSeq) {
        // TODO: 사용자 인증 확인 및 대행사 신청 정보 저장
        return insert;
    }

    /**
     * 체험단 신규 모집을 처리하는 메소드
     * @param insert 체험단 모집 정보
     * @param userSeq 사용자 식별자
     */
    public void createCampaign(CampaignInsertRequest insert, Integer userSeq) {

        switch (insert.getType()) {
            case 1: // 방문형, 포장형
                validateVisitOrPackageCampaign(insert);
                break;
            case 2: // 구매형, 배송형, 기자단형
                validatePurchaseOrDeliveryCampaign(insert);
                break;
            default:
                throw new IllegalArgumentException("잘못된 체험단 유형입니다.");
        }

        Campaign campaign = new Campaign();
        campaign.setTitle(insert.getTitle());
        campaign.setContents(insert.getContents());
        campaign.setCampaignLink(insert.getCampaignLink());
        campaign.setCity(insert.getCity());
        campaign.setDistrict(insert.getDistrict());
        campaign.setApplicationStartDate(parseDate(insert.getApplicationStartDate()));
        campaign.setApplicationEndDate(parseDate(insert.getApplicationEndDate()));
        campaign.setExperienceStartDate(parseDate(insert.getExperienceStartDate()));
        campaign.setExperienceEndDate(parseDate(insert.getExperienceEndDate()));
        campaign.setRecruiter(userSeq);
        campaign.setStatus(CampaignStatus.DRAFT.getCode());
        campaign.setType(String.valueOf(insert.getType()));

        campaignRepository.save(campaign);
    }

    /**
     * 방문형, 포장형의 체험단을 등록하는 메소드
     * @param insert
     */
    private void validateVisitOrPackageCampaign(CampaignInsertRequest insert) {
        if (insert.getLocation() == null || insert.getLocation().isEmpty()) {
            throw new IllegalArgumentException("방문형, 포장형 체험단은 위치 정보가 필수입니다.");
        }
        if (insert.getExperienceStartDate() == null || insert.getExperienceEndDate() == null) {
            throw new IllegalArgumentException("체험 날짜는 필수입니다.");
        }
    }

    /**
     * 구매형, 배송형, 기자단형의 체험단을 등록하는 메소드
     * @param insert
     */
    private void validatePurchaseOrDeliveryCampaign(CampaignInsertRequest insert) {
        if (insert.getCampaignLink() == null || insert.getCampaignLink().isEmpty()) {
            throw new IllegalArgumentException("구매형, 배송형, 기자단 유형 체험단은 구매 URL이 필수입니다.");
        }
    }

    /**
     * 문자열 날짜를 Date 객체로 변환하는 메서드
     * @param dateString
     * @return
     */
    private Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("잘못된 날짜 형식입니다.");
        }
    }

    /**
     * 캠페인을 취소하는 메소드
     * @param campaignId 캠페인 식별자
     * @param userSeq 사용자 식별자
     * @throws IllegalArgumentException 해당 캠페인이 존재하지 않을 때 발생
     * @throws IllegalStateException 캠페인 상태가 취소 가능한 상태가 아닐 때 발생
     */
    public void cancelCampaign(String campaignId, Integer userSeq) {
        Campaign campaign = campaignRepository.findById(Integer.parseInt(campaignId))
                .orElseThrow(() -> new IllegalArgumentException("해당 캠페인이 존재하지 않습니다."));

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        if (campaign.getStatus() == CampaignStatus.COMPLETE.getCode()) {
            throw new IllegalStateException("완료된 캠페인은 취소할 수 없습니다.");
        }

        if (campaign.getStatus() == CampaignStatus.DRAFT.getCode() ||
                campaign.getStatus() == CampaignStatus.READY.getCode()) {

            campaign.setStatus(CampaignStatus.CANCELED.getCode());
            campaignRepository.save(campaign);

        } else if (campaign.getStatus() == CampaignStatus.ACTIVE.getCode()) {
            campaign.setStatus(CampaignStatus.CANCELED.getCode());
            campaignRepository.save(campaign);

            user.setPenalty(user.getPenalty() + 1);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("이 상태에서는 캠페인을 취소할 수 없습니다.");
        }
    }

    /**
     * 체험단 상세 정보 조회하는 메소드
     * @param id 체험단 식별자
     * @param userSeq 사용자 식별자
     * @return 체험단 상세 정보
     */
    public Object getCampaignDetail(String id, Integer userSeq) {
        // TODO: 체험단 상세 정보 조회
        return null;
    }

    /**
     * 체험단 진행을 시작하는 메소드
     * @param seq 체험단 식별자
     * @param userSeq 사용자 식별자
     */
    public void startCampaign(Integer seq, Integer userSeq) {
        // TODO: 체험단 진행 상태 업데이트
    }

    /**
     * 체험단 신청 인플루언서 목록 조회하는 메소드
     * @param seq 체험단 식별자
     * @param userSeq 사용자 식별자
     * @return 인플루언서 목록
     */
    public List<Object> getApplicationInfluencerList(Integer seq, Integer userSeq) {
        // TODO: 인플루언서 목록 조회
        return new ArrayList<>();
    }

    /**
     * 인플루언서 목록을 다운로드하는 메소드
     * @param seq 체험단 식별자
     * @param userSeq 사용자 식별자
     * @return 다운로드 링크
     */
    public Object downloadInfluencerList(Integer seq, Integer userSeq) {
        // TODO: 인플루언서 목록 다운로드 링크 생성
        return null;
    }

    /**
     * 선정된 인플루언서 목록 조회하는 메소드
     * @param seq 체험단 식별자
     * @param userSeq 사용자 식별자
     * @return 인플루언서 목록
     */
    public List<Object> getSelectedInfluencerList(Integer seq, Integer userSeq) {
        // TODO: 선정된 인플루언서 목록 조회
        return new ArrayList<>();
    }

    /**
     * 커뮤니티 글 목록을 조회하는 메소드
     * @param page 페이지 번호
     * @param searchType 검색 유형
     * @param searchWord 검색어
     * @param userSeq 사용자 식별자
     * @return 커뮤니티 글 목록
     */
    public List<Object> getCommunityList(Integer page, String searchType, String searchWord, Integer userSeq) {
        // TODO: 커뮤니티 글 목록 조회
        return new ArrayList<>();
    }

    /**
     * 커뮤니티 글 추가하는 메소드
     * @param postRequest 커뮤니티 글 정보
     * @param userSeq 사용자 식별자
     */
    public void addCommunityPost(CommunityPostRequest postRequest, Integer userSeq) {
        // TODO: 커뮤니티 글 추가
    }

    /**
     * 커뮤니티 글 수정하는 메소드
     * @param updateRequest 수정 정보
     * @param userSeq 사용자 식별자
     */
    public void updateCommunityPost(CommunityPostUpdateRequest updateRequest, Integer userSeq) {
        // TODO: 커뮤니티 글 수정
    }

    /**
     * 커뮤니티 글 삭제하는 메소드
     * @param deleteRequest 삭제 정보
     * @param userSeq 사용자 식별자
     */
    public void deleteCommunityPosts(CommunityPostDeleteRequest deleteRequest, Integer userSeq) {
        // TODO: 커뮤니티 글 삭제
    }

    /**
     * 커뮤니티 글 상세 정보 조회하는 메소드
     * @param seq 글 식별자
     * @param userSeq 사용자 식별자
     * @return 글 상세 정보
     */
    public Object getCommunityDetail(Integer seq, Integer userSeq) {
        // TODO: 커뮤니티 글 상세 정보 조회
        return null;
    }

    /**
     * 포인트를 충전하는 메소드
     * @param paymentRequest 충전 정보
     * @param userSeq 사용자 식별자
     */
    public void deposits(PaymentRequest paymentRequest, Integer userSeq) {
        // TODO: 포인트 충전 처리
    }

    /**
     * 프로필을 수정하는 메소드
     * @param userSeq 사용자 식별자
     * @param address 주소
     * @param addressDetail 상세 주소
     * @param attachment 첨부 파일
     * @param businessNumber 사업자 번호
     * @param company 회사명
     * @param email 이메일
     * @param name 이름
     * @param phone 전화번호
     * @param postalCode 우편번호
     * @param profile 프로필 파일
     * @param pw 비밀번호
     * @param representative 대표자
     */
    public void updateProfile(Integer userSeq, String address, String addressDetail, MultipartFile attachment, String businessNumber,
                              String company, String email, String name, String phone, Integer postalCode, MultipartFile profile,
                              String pw, String representative) {
        // TODO: 프로필 정보 업데이트
    }

    /**
     * 리뷰어를 선정하는 메소드
     * @param selectRequest 선정 정보
     * @param userSeq 사용자 식별자
     */
    public void selectReviewer(ReviewerSelectRequest selectRequest, Integer userSeq) {
        // TODO: 리뷰어 선정 처리
    }
}