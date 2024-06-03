package kr.co.dain_review.be.model.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import kr.co.dain_review.be.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtService jwtService;
    private final TokenProvider tokenProvider;
    private final JwtDecoder jwtDecoder;
    public static final String AUTHORIZATION_HEADER = "Authorization";

//    기존 필터
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        if (resolveToken(httpServletRequest)!=null && !resolveToken(httpServletRequest).equals("")) {
//            String token = resolveToken(httpServletRequest);
//            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
//                Authentication authentication = tokenProvider.getAuthentication(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        try {
            String token = resolveToken(httpServletRequest);
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 추가적으로 클라이언트에게 에러 메시지를 보낼 수 있습니다.
            JSONObject json = new JSONObject();
            json.put("message", "Token expired");
            httpServletResponse.getWriter().write(json.toString());
            return;
        }
        filterChain.doFilter(request, response);
    }



    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }
}
