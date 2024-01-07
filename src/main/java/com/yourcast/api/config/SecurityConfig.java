package com.yourcast.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public DefaultSecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
    security
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/actuator/**", "/v3/api-docs/**", "/webjars/**", "/swagger-ui.html", "/swagger-ui*/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/v2/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/v2/**").permitAll()
            .anyRequest().authenticated()
        )
        .cors(request -> {
          CorsConfiguration cors = new CorsConfiguration();
          cors.setAllowedOrigins(List.of("*"));
          cors.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));
          cors.setAllowedHeaders(List.of("*"));
        })
        .sessionManagement(sess -> sess
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

    return security.build();
  }
}