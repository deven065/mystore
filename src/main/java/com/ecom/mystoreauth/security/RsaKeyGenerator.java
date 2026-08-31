package com.ecom.mystoreauth.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RsaKeyGenerator {

    public static void main(String[] args) throws Exception {

        // Created an RSA key pair generator
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        // Generated a 2048-bit RSA key pair
        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Convert the private key into PEM format
        String privateKeyPem =
                "-----BEGIN PRIVATE KEY-----\n"
                        + Base64.getMimeEncoder(64, new byte[]{'\n'})
                        .encodeToString(
                                keyPair.getPrivate().getEncoded()
                        )
                        + "\n-----END PRIVATE KEY-----\n";

        // Convert the public key into PEM format
        String publicKeyPem =
                "-----BEGIN PUBLIC KEY-----\n"
                        + Base64.getMimeEncoder(64, new byte[]{'\n'})
                        .encodeToString(
                                keyPair.getPublic().getEncoded()
                        )
                        + "\n-----END PUBLIC KEY-----\n";

        // Location where our keys will be stored
        Path certsDirectory = Path.of(
                System.getProperty("user.dir"),
                "src",
                "main",
                "resources",
                "carts"
        );

        Files.createDirectories(certsDirectory);

        // Save the private key
        Files.writeString(
                certsDirectory.resolve("private.pem"),
                privateKeyPem
        );

        // Save the public key
        Files.writeString(
                certsDirectory.resolve("public.pem"),
                publicKeyPem
        );

        System.out.println(
                "RSA key pair generated successfully."
        );
    }
}