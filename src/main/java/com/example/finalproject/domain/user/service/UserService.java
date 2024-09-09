package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.user.dto.Register;
import com.example.finalproject.domain.user.entity.Businesses;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.dto.UserInfo;
import com.example.finalproject.domain.user.repository.BusinessesRepository;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.util.FileUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final InfluencerRepository influencerRepository;
	private final BusinessesRepository businessesRepository;

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

	public void signup(Register register) {
		String id = UUID.randomUUID().toString();
		User user = new User();
		/*if (register.getProfile() != null) {
			String fileName = FileUtils.setNewName(register.getFileName());
			FileUtils.saveFile(register.getProfile(), id + "/" + fileName);
			user.setProfile(fileName);
		}*/
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


}
