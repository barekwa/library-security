package com.example.project.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.project.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;
import java.util.Map;

@Configuration
@PropertySource(value = "classpath:application.properties")
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${my.jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;


    public String generateToken(User user) {
        Date issuedAt = new Date();
        return JWT.create()
                .withIssuedAt(issuedAt)
                .withClaim("username", user.getName())
                .withClaim("role", user.getRole().getName())
                .withIssuer(jwtIssuer)
                .sign(Algorithm.HMAC256(secret));
    }

    public Map<String, Claim> decodeToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

