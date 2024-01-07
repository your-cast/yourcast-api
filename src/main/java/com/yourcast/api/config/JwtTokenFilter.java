package com.yourcast.api.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yourcast.api.util.JwtTokenUtil;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;

  @Override
  @SneakyThrows
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (isEmpty(header) || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.split(" ")[1].trim();
    if (token.isEmpty()) {
      log.warn("Token invalid.");
      filterChain.doFilter(request, response);
      return;
    }

    if (!jwtTokenUtil.validateAccessToken(token)) {
      log.warn("Token invalid.");
      filterChain.doFilter(request, response);
    }
  }
}