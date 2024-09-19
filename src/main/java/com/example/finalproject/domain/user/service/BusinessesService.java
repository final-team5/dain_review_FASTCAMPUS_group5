package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.campaign.dto.request.CampaignInsertRequest;
import com.example.finalproject.domain.campaign.dto.request.ReviewerSelectRequest;
import com.example.finalproject.domain.campaign.dto.response.CampaignDetailResponse;
import com.example.finalproject.domain.campaign.dto.response.ResultReportResponse;
import com.example.finalproject.domain.campaign.entity.Campaign;
import com.example.finalproject.domain.campaign.entity.CampaignStatus;
import com.example.finalproject.domain.campaign.entity.ResultReport;
import com.example.finalproject.domain.campaign.repository.CampaignRepository;
import com.example.finalproject.domain.campaign.repository.ResultReportRepository;
import com.example.finalproject.domain.payment.dto.request.PaymentRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostDeleteRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostRequest;
import com.example.finalproject.domain.post.dto.request.CommunityPostUpdateRequest;
import com.example.finalproject.domain.user.dto.UserInfo;
import com.example.finalproject.domain.user.dto.request.AgencyInsertRequest;
import com.example.finalproject.domain.user.dto.response.InfluencerDetailResponse;
import com.example.finalproject.domain.user.entity.Agency;
import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.AgencyRepository;
import com.example.finalproject.domain.user.repository.BusinessesRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.DateTime;
import lombok.SneakyThrows;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.message.AuthException;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessesService {

    @Autowired
    private ResultReportRepository resultReportRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private InfluencerService influencerService;

    /**
     * 현재 인증된 사용자의 식별자를 가져오는 메서드
     *
     * @return 현재 인증된 사용자의 식별자 (userSeq)
     * @throws AuthException 인증되지 않은 사용자가 요청할 때 발생
     */
    @SneakyThrows
    private Integer getCurrentUserSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthException();
        }
        UserInfo userInfo = userService.findByEmail(authentication.getName());
        return userInfo.getSeq();
    }

    /**
     * 대행사 신청을 처리하는 메소드
     *
     * @param request 대행사 신청 정보
     */
    @Transactional
    public void applyAgency(AgencyInsertRequest request) {
        Integer userSeq = getCurrentUserSeq();

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        user.setAddress(request.getAddress());
        user.setAddressDetail(request.getAddressDetail());
        user.setPostalCode(Integer.parseInt(request.getPostalCode()));
        userRepository.save(user);

        Businesses businesses = businessesRepository.findByUser(user)
                .orElse(new Businesses());

        businesses.setUser(user);
        businesses.setCompany(request.getCompany());
        businesses.setBusinessNumber(request.getBusinessNumber());
        businesses.setRepresentative(request.getRepresentative());
        businesses.setAttachedFile(request.getAttachment());
        businessesRepository.save(businesses);

        // TODO: 관리자 기능 구현
        Agency agency = new Agency();
        agency.setReason("신청 사유");
        agency.setStatus(1);  // 초기 상태 (0: 검토 중, 1: 승인, 2: 거절)
        agency.setCreateDate(new Date());
        agencyRepository.save(agency);
    }

    /**
     * 새로운 체험단을 등록하는 메서드
     *
     * @param insert 체험단 등록 요청 정보 (CampaignInsertRequest)
     * @throws IllegalArgumentException 입력값이 잘못되었을 때 발생
     */
    public void createCampaign(CampaignInsertRequest insert) {
        Integer userSeq = getCurrentUserSeq();
        validateCampaignInsertRequest(insert);

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
        // TODO: 관리자 기능 구현
        campaign.setStatus(CampaignStatus.DRAFT.getCode());
        campaign.setType(insert.getType());

        campaign.setCategory(String.valueOf(insert.getCategory()));
        campaign.setPlatform(insert.getPlatform());
        campaign.setImage(insert.getImage());
        campaign.setKeyword1(insert.getKeyword1());
        campaign.setKeyword2(insert.getKeyword2());
        campaign.setKeyword3(insert.getKeyword3());
        campaign.setMission(insert.getMission());
        campaign.setMonday(insert.isMonday() ? 1 : 0);
        campaign.setTuesday(insert.isTuesday() ? 1 : 0);
        campaign.setWednesday(insert.isWednesday() ? 1 : 0);
        campaign.setThursday(insert.isThursday() ? 1 : 0);
        campaign.setFriday(insert.isFriday() ? 1 : 0);
        campaign.setSaturday(insert.isSaturday() ? 1 : 0);
        campaign.setSunday(insert.isSunday() ? 1 : 0);
        campaign.setPoint(insert.getPoint());
        campaign.setService(insert.getService());

        campaign.setExperienceStartTime(formatTime(insert.getExperienceStartDate(), insert.getExperienceStartTime()));
        campaign.setExperienceEndTime(formatTime(insert.getExperienceEndDate(), insert.getExperienceEndTime()));
        campaign.setSegment(insert.getSegment());

        String fullAddress = insert.getCity() + " " + insert.getDistrict();
        setLatitudeAndLongitude(campaign, fullAddress);

        campaignRepository.save(campaign);

        campaignRepository.save(campaign);
    }

    /**
     * 문자열 형식의 날짜와 시간을 RFC3339 형식의 DateTime 객체로 변환하는 메서드
     *
     * @param date 날짜 문자열 (yyyy-MM-dd 형식)
     * @param time 시간 문자열 (HH:mm 형식)
     * @return RFC3339 형식의 DateTime 객체
     */
    private Date formatTime(String date, String time) {
        if (date == null || time == null) {
            return null;
        }
        String dateTimeString = date + "T" + time + ":00Z";
        return new Date(dateTimeString);
    }

    /**
     * 주소를 기반으로 위도와 경도를 설정하는 메서드
     *
     * @param campaign 체험단 엔티티
     * @param address  주소 문자열
     */
    private void setLatitudeAndLongitude(Campaign campaign, String address) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                    .queryParam("q", address)
                    .queryParam("format", "json")
                    .build()
                    .toUriString();

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.isArray() && root.size() > 0) {
                JsonNode location = root.get(0);
                campaign.setLatitude(location.get("lat").asDouble());
                campaign.setLongitude(location.get("lon").asDouble());
            } else {
                System.out.println("주소로 위도/경도를 찾을 수 없습니다: " + address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 체험단 등록 요청의 유효성을 검사하는 메서드
     *
     * @param insert 체험단 등록 요청 정보
     * @throws IllegalArgumentException 필수 필드가 누락되거나 잘못된 값일 때 발생
     */
    private void validateCampaignInsertRequest(CampaignInsertRequest insert) {
        if (insert.getTitle() == null || insert.getTitle().isEmpty()) {
            throw new IllegalArgumentException("체험단 제목은 필수입니다.");
        }
        if (insert.getContents() == null || insert.getContents().isEmpty()) {
            throw new IllegalArgumentException("체험단 내용은 필수입니다.");
        }
        if (insert.getApplicationStartDate() == null || insert.getApplicationEndDate() == null) {
            throw new IllegalArgumentException("신청 기간은 필수입니다.");
        }
        if (insert.getExperienceStartDate() == null || insert.getExperienceEndDate() == null) {
            throw new IllegalArgumentException("체험 기간은 필수입니다.");
        }
        if (insert.getCampaignLink() == null || insert.getCampaignLink().isEmpty()) {
            throw new IllegalArgumentException("체험단 링크는 필수입니다.");
        }
        // 날짜 검증
        Date applicationStartDate = parseDate(insert.getApplicationStartDate());
        Date applicationEndDate = parseDate(insert.getApplicationEndDate());
        if (applicationStartDate.after(applicationEndDate)) {
            throw new IllegalArgumentException("신청 시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        }
        Date experienceStartDate = parseDate(insert.getExperienceStartDate());
        Date experienceEndDate = parseDate(insert.getExperienceEndDate());
        if (experienceStartDate.after(experienceEndDate)) {
            throw new IllegalArgumentException("체험 시작 날짜는 종료 날짜보다 이전이어야 합니다.");
        }
    }

    /**
     * 체험단을 취소하는 메서드
     *
     * @param campaignId 취소할 체험단의 식별자 (ID)
     * @throws IllegalArgumentException 해당 체험단이 존재하지 않을 때 발생
     * @throws IllegalStateException 해당 체험단을 취소할 권한이 없거나 취소할 수 없는 상태일 때 발생
     */
    public void cancelCampaign(String campaignId) {
        Integer userSeq = getCurrentUserSeq();
        Campaign campaign = campaignRepository.findById(Integer.parseInt(campaignId))
                .orElseThrow(() -> new IllegalArgumentException("해당 체험단이 존재하지 않습니다."));

        if (!campaign.getRecruiter().equals(userSeq)) {
            throw new IllegalStateException("해당 체험단을 취소할 권한이 없습니다.");
        }

        if (campaign.getStatus() == CampaignStatus.COMPLETE.getCode()) {
            throw new IllegalStateException("완료된 체험단은 취소할 수 없습니다.");
        }

        if (campaign.getStatus() == CampaignStatus.DRAFT.getCode() ||
                campaign.getStatus() == CampaignStatus.READY.getCode()) {
            campaign.setStatus(CampaignStatus.CANCELED.getCode());
            campaignRepository.save(campaign);
        } else {
            throw new IllegalStateException("현재 상태에서 체험단을 취소할 수 없습니다.");
        }
    }

    /**
     * 체험단의 상세 정보를 조회하는 메서드
     *
     * @param id 조회할 체험단의 식별자 (ID)
     * @return 조회한 체험단의 상세 정보 (CampaignDetailResponse)
     * @throws IllegalArgumentException 해당 체험단이 존재하지 않을 때 발생
     */
    public ResponseEntity<Object> getCampaignDetail(String id) {
        Integer userSeq = getCurrentUserSeq();
        Campaign campaign = campaignRepository.findById(Integer.parseInt(id)).orElse(null);

        if (campaign == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CampaignDetailResponse());
        }

        Optional<User> optionalUser = userRepository.findById(campaign.getRecruiter());
        User user = optionalUser.orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new CampaignDetailResponse());
        }

        if (!user.getSeq().equals(userSeq)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 체험단에 접근할 권한이 없습니다.");
        }

        List<InfluencerDetailResponse> influencerList = influencerService.getInfluencersForCampaign(Long.valueOf(campaign.getSeq()));

        CampaignDetailResponse response = convertCampaignToCampaignDetailResponse(campaign, influencerList);
        return ResponseEntity.ok(response);
    }

    /**
     * Campaign 엔티티를 CampaignDetailResponse DTO로 변환하는 메서드
     *
     * @param campaign 변환할 Campaign 엔티티
     * @return 변환된 CampaignDetailResponse 객체
     */
    private CampaignDetailResponse convertCampaignToCampaignDetailResponse(Campaign campaign, List<InfluencerDetailResponse> influencerList) {
        User user = campaign.getUser();
        Businesses business = businessesRepository.findByUser(user).orElse(null);

        return new CampaignDetailResponse(
                campaign.getSeq(),
                campaign.getTitle(),
                campaign.getContents(),
                business != null ? business.getCompany() : null,
                user != null ? user.getAddress() : null,
                user != null ? user.getPhone() : null,
                campaign.getStatus(),
                campaign.getApplicationStartDate(),
                campaign.getApplicationEndDate(),
                campaign.getExperienceStartDate(),
                campaign.getExperienceEndDate(),
                campaign.getCity(),
                campaign.getDistrict(),
                campaign.getRecruiter(),
                campaign.getPlatform(),
                campaign.getCategory(),
                campaign.getType(),
                campaign.getSegment(),
                campaign.getCampaignLink(),
                campaign.getImage(),
                campaign.getKeyword1(),
                campaign.getKeyword2(),
                campaign.getKeyword3(),
                campaign.getMission(),
                campaign.getService(),
                campaign.getPoint(),
                influencerList,
                campaign.getApplicationParticipantsDate(),
                campaign.getReviewEndDate(),
                campaign.getLatitude(),
                campaign.getLongitude(),
                campaign.getRegisteredAt(),
                campaign.getUpdatedAt()
        );
    }

    /**
     * 체험단 진행을 시작하는 메서드
     *
     * @param seq 진행할 체험단의 식별자 (ID)
     * @throws IllegalArgumentException 해당 체험단이 존재하지 않을 때 발생
     * @throws IllegalStateException 해당 체험단을 진행할 권한이 없거나 준비 상태가 아닐 때 발생
     */
    public void startCampaign(Integer seq) {
        Integer userSeq = getCurrentUserSeq();
        Campaign campaign = campaignRepository.findById(seq)
                .orElseThrow(() -> new IllegalArgumentException("해당 체험단이 존재하지 않습니다."));

        if (!campaign.getRecruiter().equals(userSeq)) {
            throw new IllegalStateException("해당 체험단을 진행할 권한이 없습니다.");
        }

        if (campaign.getStatus() != CampaignStatus.READY.getCode()) {
            throw new IllegalStateException("체험단이 준비 상태가 아닙니다.");
        }

        campaign.setStatus(CampaignStatus.ACTIVE.getCode());
        campaignRepository.save(campaign);
    }

    /**
     * 문자열로 된 날짜를 Date 객체로 변환하는 메서드
     *
     * @param dateString 변환할 날짜 문자열 (yyyy-MM-dd 형식)
     * @return 변환된 Date 객체
     * @throws IllegalArgumentException 날짜 형식이 잘못되었을 때 발생
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
     * 결과보고서를 조회하는 메서드
     *
     * @param campaignSeq 결과보고서의 체험단 식별자
     * @return 결과보고서 객체
     * @throws IllegalArgumentException 결과보고서가 존재하지 않거나 권한이 없을 때 발생
     */
    @Transactional
    public ResultReportResponse getResultReport(Integer campaignSeq) {
        // TODO: 결과보고서 생성 및 업데이트 기능 추가
        if (campaignSeq == null) {
            throw new IllegalArgumentException("체험단 식별자는 필수입니다.");
        }

        Integer userSeq = getCurrentUserSeq();

        Campaign campaign = campaignRepository.findById(campaignSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 체험단이 존재하지 않습니다."));

        if (!campaign.getRecruiter().equals(userSeq)) {
            throw new IllegalArgumentException("해당 체험단에 접근할 권한이 없습니다.");
        }

        ResultReport resultReport = resultReportRepository.findByCampaignId(campaignSeq);
        if (resultReport == null) {
            throw new IllegalArgumentException("결과보고서를 찾을 수 없습니다.");
        }

        ResultReportResponse response = mapResultReportToResponse(resultReport, campaign);

        return response;
    }

    /**
     * ResultReport 엔티티를 ResultReportResponse DTO로 변환하는 메서드
     *
     * @param resultReport 결과보고서 엔티티
     * @param campaign 체험단 엔티티
     * @return 변환된 ResultReportResponse DTO
     */
    private ResultReportResponse mapResultReportToResponse(ResultReport resultReport, Campaign campaign) {
        ResultReportResponse response = new ResultReportResponse();
        response.setReportId(resultReport.getReportId());
        response.setCampaignTitle(campaign.getTitle());
        response.setTotalParticipants(resultReport.getTotalParticipants());
        response.setCompletedParticipants(resultReport.getCompletedParticipants());
        response.setStartDate(resultReport.getStartDate().toString());
        response.setEndDate(resultReport.getEndDate().toString());

        ResultReportResponse.PerformanceMetrics metrics = new ResultReportResponse.PerformanceMetrics();
        metrics.setReach(resultReport.getReach());
        metrics.setEngagement(resultReport.getEngagement());
        metrics.setConversion(resultReport.getConversion());
        response.setPerformanceMetrics(metrics);

        response.setSummary(resultReport.getSummary());
        return response;
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