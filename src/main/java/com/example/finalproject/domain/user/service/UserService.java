package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.campaign.dto.request.CampaignCancelRequest;
import com.example.finalproject.domain.campaign.dto.request.CampaignInsertRequest;
import com.example.finalproject.domain.campaign.dto.request.ReviewerSelectRequest;
import com.example.finalproject.domain.payment.dto.request.PaymentRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostUpdateRequest;
import com.example.finalproject.domain.user.dto.request.AgencyInsertRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

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
        // TODO: 사용자 인증 확인 및 체험단 모집 정보 저장
    }

    /**
     * 검수 중인 체험단 취소하는 메소드
     * @param cancelRequest 취소 정보
     */
    public void cancelCampaign(CampaignCancelRequest cancelRequest) {
        // TODO: 체험단 취소 처리
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