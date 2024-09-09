package com.example.finalproject.global.jwt;

import com.example.finalproject.domain.user.dto.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements InitializingBean {
	private static final String AUTHORITIES_KEY = "role";
	private final String secret;
	private final long tokenValidityInMilliseconds;
	private Key key;

	public TokenProvider(
		@Value("${jwt.secret}") String secret,
		@Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds
	) {
		this.secret = secret;
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(secret);
		SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(secretKey);
		this.key = secretKey;
	}

	public String createToken(UserInfo user) throws ParseException {
		// 토큰의 expire 시간을 설정
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();

		Date validity = new Date(timeInSecs + tokenValidityInMilliseconds);
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", user.getSeq());
		map.put("id", user.getId());
		map.put("email", user.getEmail());
//		map.put("pw", user.getPw());
		map.put("name", user.getName());
		map.put("type", user.getType());
		map.put("role", user.getRole());
		return Jwts.builder()
			.setSubject("subject")
			.setClaims(map)
			.signWith(key, SignatureAlgorithm.HS512)
			.setExpiration(validity)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(
			claims.get("email").toString(), claims.get("email").toString(), authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) throws ExpiredJwtException {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException exception) {
			System.out.println("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			throw e; // 만료된 토큰 예외를 다시 던집니다.
		} catch (UnsupportedJwtException e) {
			System.out.println("지워노디지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			System.out.println("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	public Claims getClaims(String token) {
		return Jwts.parser()
			.setSigningKey(key)
			.parseClaimsJws(token).getBody();
	}

	public Integer getSeq(String token){
		return (Integer) getClaims(token).get("seq");
	}

	public String getName(String token) {return getClaims(token).get("name").toString();}

	public String getEmail(String token) {return getClaims(token).get("email").toString();}

	public String getRole(String token) {
		return getClaims(token).get("role").toString();
	}

	public String getNickname(String token){
		return (String) getClaims(token).get("nickname");
	}

	public String getPhone(String token) {
		return (String) getClaims(token).get("phone");
	}

	public Date getExpireDate(String token){
		long timestamp = Long.parseLong(getClaims(token).get("exp").toString());
		Date date = new Date(timestamp*1000L);
		return date;
	}

	public Integer getType(String token) {
		return (Integer) getClaims(token).get("type");
	}
}
