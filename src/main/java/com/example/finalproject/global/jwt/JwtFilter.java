package com.example.finalproject.global.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.util.StringUtils;
import org.json.JSONObject;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

	private final TokenProvider tokenProvider;
	private final JwtDecoder jwtDecoder;
	public static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
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

			JSONObject json = new JSONObject();
			json.put("message", "Token expired");
			httpServletResponse.getWriter().write(json.toString());
			return;
		}
		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		return request.getHeader(AUTHORIZATION_HEADER);
	}
}
