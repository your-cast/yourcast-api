package com.yourcast.api.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.yourcast.api.hibernate.entity.UserEntity;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  @Value("${jwt.expire:60000}")
  private Long EXPIRE_DURATION;

  @Value("${jwt.secret}")
  private String SECRET_KEY;

  @Value("${jwt.issuer}")
  private String ISSUER;

  public String generateAccessToken(UserEntity user) {
    return Jwts.builder()
        .setSubject(String.format("{user: %s, email: %s}", user.getId(), user.getEmail()))
        .setIssuer(ISSUER)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      log.error("JWT token expired: {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      log.error("JWT token is null, empty or only whitespace: {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      log.error("JWT token is invalid", ex);
    } catch (UnsupportedJwtException ex) {
      log.error("JWT token is not supported", ex);
    } catch (SignatureException ex) {
      log.error("Signature validation failed");
    }

    return false;
  }

  public String extractEmail(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String getSubject(String token) {
    return parseClaims(token).getSubject();
  }

  private Claims parseClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
}