package com.tennismauel.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class JwtConfig {
    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;

    @Bean
    public JwtDecoder jwtDecoder(){
        log.debug(tokenSecret);
        SecretKey secretKey=new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), "HMACSHA512");

        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
    }
}