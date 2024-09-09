package com.example.finalproject.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.finalproject.domain.user.dto.Register;
import com.example.finalproject.domain.user.dto.UserInfo;
import com.example.finalproject.domain.user.dto.request.ChangePasswordRequest;
import com.example.finalproject.domain.user.dto.response.TokenRefreshResponse;
import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.BusinessesRepository;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import com.example.finalproject.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final InfluencerRepository influencerRepository;
	private final BusinessesRepository businessesRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public UserInfo getUser(String email, Integer loginType) {
		User user = userRepository.getByEmailAndLoginType(email, loginType);

		return UserInfo.builder()
			.seq(user.getSeq())
			.email(user.getEmail())
			.id(user.getId())
			.pw(user.getPw())
			.role(user.getRole())
			.name(user.getName())
			.phone(user.getPhone())
			.signupSource(user.getSignupSource())
			.postalCode(user.getPostalCode())
			.address(user.getAddress())
			.addressDetail(user.getAddressDetail())
			.point(user.getPoint())
			.status(user.getStatus())
			.cancel(user.getCancel())
			.penalty(user.getPenalty())
			.type(user.getType())
			.build()
			;
	}

	public Optional<User> findByUser(String email, Integer loginType) {
		return userRepository.findByEmailAndLoginType(email, loginType);
	}

	public UserInfo findByUsername(String username) {
		User user = userRepository.findByName(username)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

		return UserInfo.builder()
				.seq(user.getSeq())
				.email(user.getEmail())
				.id(user.getId())
				.pw(user.getPw())
				.role(user.getRole())
				.name(user.getName())
				.phone(user.getPhone())
				.signupSource(user.getSignupSource())
				.postalCode(user.getPostalCode())
				.address(user.getAddress())
				.addressDetail(user.getAddressDetail())
				.point(user.getPoint())
				.status(user.getStatus())
				.cancel(user.getCancel())
				.penalty(user.getPenalty())
				.type(user.getType())
				.build();
	}

	public boolean checkNickname(String nickname) {
		return influencerRepository.existsByNickname(nickname);
	}

	public boolean checkCompany(String company) {
		return businessesRepository.existsByCompany(company);
	}

	public boolean checkEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public boolean checkPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}

	@Transactional
	public void signup(Register register) {
		String id = UUID.randomUUID().toString();
		User user = new User();
		user.userEntity(id, register);

		userRepository.save(user);

		if (register.getRole().equals("ROLE_INFLUENCER")) {
			Influencer influencer = new Influencer();
			influencer.influencerEntity(user, register);

			influencerRepository.save(influencer);
		} else if (register.getRole().equals("ROLE_BUSINESSES")) {
			Businesses businesses = Businesses.builder()
				.user(user)
				.company(register.getCompany())
				.build()
				;
			businessesRepository.save(businesses);
		}
	}

	/**
	 * 이미지 파일 등록 기능
	 *
	 * @param multipartFile : 등록할 이미지 파일
	 * @param dirName : S3 저장소 디렉토리 명
	 * @return : 저장한 이미지 파일 url
	 */
	public String uploadImage(MultipartFile multipartFile, String dirName) throws IOException {
		// 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
		String originalFilename = multipartFile.getOriginalFilename();

		// UUID 를 파일명에 추가
		String uuid = UUID.randomUUID().toString();
		String uniqueFileName = uuid + "_" + originalFilename.replaceAll("\\s", "_");

		// 파일 이름을 URL 인코딩 처리
		String encodedFileName = URLEncoder.encode(uniqueFileName, "UTF-8").replaceAll("\\+", "%20");

		String fileName = dirName + "/" + encodedFileName;

		File uploadFile = convert(multipartFile);

		String uploadImageUrl = putS3(uploadFile, fileName);
		removeNewFile(uploadFile);

		return uploadImageUrl;
	}

	/**
	 * 이미지 파일 수정 기능
	 *
	 * @param oldImageUrl : 기존 저장했던 이미지 파일의 url
	 * @param newImageFile : 새로 업데이트 할 이미지 파일
	 * @param dirName : S3 에 저장할 디렉토리명
	 * @return : 새로 업데이트한 이미지 url
	 */
	@Transactional
	public String updateImage(String oldImageUrl, MultipartFile newImageFile, String dirName) throws IOException {
		// 기존 파일 삭제
		String[] split = oldImageUrl.split("user-profile-images/");

		// 파일 이름을 URL 인코딩 처리
		String encodedFileName = URLEncoder.encode(split[1], "UTF-8").replaceAll("\\+", "%20");

		oldImageUrl = "user-profile-images/" + encodedFileName;

		log.info("oldImageUrl : {}", oldImageUrl);

		deleteImage(oldImageUrl);

		// 새 파일 업로드
		return uploadImage(newImageFile, dirName);
	}

	/**
	 * 비밀번호 변경 기능
	 *
	 * @param userEmail : 회원 email ID
	 * @param changePasswordRequest : 변경할 비밀번호와 확인용 비밀번호 정보
	 */
	@Transactional
	public void changePassword(String userEmail, ChangePasswordRequest changePasswordRequest) {
		User user = userRepository.getUserByEmailOrException(userEmail);

		if (!changePasswordRequest.getNewPw().equals(changePasswordRequest.getPw())) {
			throw new ValidException(ValidErrorCode.PASSWORD_MISMATCH);
		}

		if (passwordEncoder.matches(changePasswordRequest.getNewPw(), user.getPw())) {
			throw new ValidException(ValidErrorCode.ALREADY_USED_PASSWORD_RECENTLY);
		}

		String encodedPassword = passwordEncoder.encode(changePasswordRequest.getNewPw());

		user.setPw(encodedPassword);
	}

	/**
	 * 회원 탈퇴 기능
	 *
	 * @param userEmail : 회원 email ID
	 */
	@Transactional
	public void withdrawalUser(String userEmail) {
		User user = userRepository.getUserByEmailOrException(userEmail);

		String role = user.getRole();

		if (role.equals("ROLE_INFLUENCER")) {
			influencerRepository.deleteByUser(user);
		}

		if (role.equals("ROLE_BUSINESSES")) {
			businessesRepository.deleteByUser(user);
		}

	}

	/**
	 * 토큰 갱신 기능
	 *
	 * @param userEmail : 회원 email ID
	 * @return : TokenRefreshResponse
	 */
	public TokenRefreshResponse refreshToken(String userEmail) throws ParseException {
		User user = userRepository.getUserByEmailOrException(userEmail);
		UserInfo userInfo = UserInfo.from(user);

		String token = tokenProvider.createToken(userInfo);
		String email = user.getEmail();
		Date expireDate = tokenProvider.getExpireDate(token);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strNowDate = simpleDateFormat.format(expireDate);

		return TokenRefreshResponse.of(token, email, strNowDate);
	}

	private File convert(MultipartFile file) throws IOException {
		String originalFileName = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString();
		String uniqueFileName = uuid + "_" + originalFileName.replaceAll("\\s", "_");

		File convertFile = new File(uniqueFileName);

		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			} catch (IOException e) {
				throw e;
			}

			return convertFile;
		}

		throw new IllegalArgumentException(String.format("파일 변환에 실패했습니다. %s", originalFileName));
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile));

		return amazonS3.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		} else {
			log.info("파일이 삭제되지 않았습니다.");
		}
	}

	public void deleteImage(String imageUrl) {

		amazonS3.deleteObject(bucket, imageUrl);
	}


}
