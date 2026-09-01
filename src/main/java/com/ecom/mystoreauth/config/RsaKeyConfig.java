package com.ecom.mystoreauth.config;


import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.source.JWKSource;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class RsaKeyConfig {

    @Bean
    public RSAPrivateKey privateKey() throws Exception {

        String key = Files.readString(
                Path.of(
                        System.getProperty("user.dir"),
                        "src",
                        "main",
                        "resources",
                        "certs",
                        "private.pem"
                )
        );

        key = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes =
                Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec keySpec =
                new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        return (RSAPrivateKey)
                keyFactory.generatePrivate(keySpec);
    }

    @Bean
    public RSAPublicKey publicKey() throws Exception {

        String key = Files.readString(
                Path.of(
                        System.getProperty("user.dir"),
                        "src",
                        "main",
                        "resources",
                        "certs",
                        "public.pem"
                )
        );

        key = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes =
                Base64.getDecoder().decode(key);

        X509EncodedKeySpec keySpec =
                new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory =
                KeyFactory.getInstance("RSA");

        return (RSAPublicKey)
                keyFactory.generatePublic(keySpec);
    }

    @Bean
    public JwtEncoder jwtEncoder(RSAPublicKey publicKey, RSAPrivateKey privateKey) throws Exception {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);

        JWKSource<SecurityContext> jwkSource =
                new ImmutableJWKSet<>(jwkSet);

        return new NimbusJwtEncoder(jwkSource);
    }
}