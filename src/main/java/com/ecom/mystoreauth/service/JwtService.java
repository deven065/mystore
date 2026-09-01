package com.ecom.mystoreauth.service;

import com.ecom.mystoreauth.entity.User;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                //  Who issues this token
                .issuer("mystore-auth")
                //  When the token was created
                .issuedAt(now)
                //  Token expires after 1 hour
                .expiresAt(now.plusSeconds(3600))
                //  User's email
                .subject(user.getEmail())
                //  User's ID
                .claim("userId", user.getId())
                //  User's role
                .claim("role", user.getRole().name())
                .build();

        return jwtEncoder
                .encode(
                        JwtEncoderParameters.from(
                                JwsHeader.with(
                                        org.springframework.security.oauth2.jose.jws.SignatureAlgorithm.RS256
                                ).build(),
                                claims
                        )
                )
                .getTokenValue();
    }
}
