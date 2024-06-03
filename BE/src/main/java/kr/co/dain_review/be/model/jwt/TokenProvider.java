package kr.co.dain_review.be.model.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.dain_review.be.model.user.User;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "role";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds;
    }

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user) throws ParseException {
        // 토큰의 expire 시간을 설정
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
//        long now = (new Date()).getTime();
        Date validity = new Date(timeInSecs + tokenValidityInMilliseconds);
        HashMap<String, Object> map = new HashMap<>();
        map.put("seq", user.getSeq());
        map.put("name", user.getName());
        map.put("type", user.getType());
        map.put("phone", user.getPhone());

        return Jwts.builder()
                .setSubject("subject")
                .setClaims(map) // 정보 저장s
                .signWith(key, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 , signature 에 들어갈 secret값 세팅
                .setExpiration(validity) // set Expire Time 해당 옵션 안넣으면 expire안함
                .compact();
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
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
        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(claims.get("email").toString(), claims.get("name").toString(), authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
//    public boolean validateToken(String token) {
//        try{
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            System.out.println(e);
//            System.out.println("잘못된 JWT 서명입니다.");
//        } catch (ExpiredJwtException e) {
//            System.out.println(e);
//            System.out.println("만료된 JWT 토큰입니다.");
//        } catch (UnsupportedJwtException e) {
//            System.out.println(e);
//            System.out.println("지원되지 않는 JWT 토큰입니다.");
//        } catch (IllegalArgumentException e) {
//            System.out.println(e);
//            System.out.println("JWT 토큰이 잘못되었습니다.");
//        }
//        return false;
//    }

    public boolean validateToken(String token) throws ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw e; // 만료된 토큰 예외를 다시 던집니다.
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Claims getClaims(String token){
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