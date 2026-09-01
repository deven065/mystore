package com.ecom.mystoreauth.security;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RsaKeyGenerator {

    public static void main(String[] args) throws Exception {

        // --------------------------------------------------
        // 1. Generate RSA key pair
        // --------------------------------------------------

        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");

        // Generate 2048-bit RSA keys
        keyPairGenerator.initialize(2048);

        KeyPair keyPair =
                keyPairGenerator.generateKeyPair();


        // --------------------------------------------------
        // 2. Convert Private Key to PEM format
        // --------------------------------------------------

        String privateKeyPem =
                "-----BEGIN PRIVATE KEY-----\n"
                        + Base64.getMimeEncoder(64, new byte[]{'\n'})
                        .encodeToString(
                                keyPair.getPrivate().getEncoded()
                        )
                        + "\n-----END PRIVATE KEY-----\n";


        // --------------------------------------------------
        // 3. Convert Public Key to PEM format
        // --------------------------------------------------

        String publicKeyPem =
                "-----BEGIN PUBLIC KEY-----\n"
                        + Base64.getMimeEncoder(64, new byte[]{'\n'})
                        .encodeToString(
                                keyPair.getPublic().getEncoded()
                        )
                        + "\n-----END PUBLIC KEY-----\n";


        // --------------------------------------------------
        // 4. Find our project directory
        // --------------------------------------------------

        Path projectDirectory =
                Path.of(System.getProperty("user.dir"));

        System.out.println(
                "Project directory: "
                        + projectDirectory.toAbsolutePath()
        );


        // --------------------------------------------------
        // 5. Create certs directory
        // --------------------------------------------------

        Path certsDirectory =
                projectDirectory
                        .resolve("src")
                        .resolve("main")
                        .resolve("resources")
                        .resolve("certs");

        Files.createDirectories(certsDirectory);

        System.out.println(
                "Keys will be stored in: "
                        + certsDirectory.toAbsolutePath()
        );


        // --------------------------------------------------
        // 6. Create private.pem
        // --------------------------------------------------

        Path privateKeyPath =
                certsDirectory.resolve("private.pem");

        Files.writeString(
                privateKeyPath,
                privateKeyPem
        );


        // --------------------------------------------------
        // 7. Create public.pem
        // --------------------------------------------------

        Path publicKeyPath =
                certsDirectory.resolve("public.pem");

        Files.writeString(
                publicKeyPath,
                publicKeyPem
        );


        // --------------------------------------------------
        // 8. Verify that the files actually exist
        // --------------------------------------------------

        System.out.println();

        System.out.println(
                "Private key exists: "
                        + Files.exists(privateKeyPath)
        );

        System.out.println(
                "Public key exists: "
                        + Files.exists(publicKeyPath)
        );

        System.out.println();

        System.out.println(
                "Private key location: "
                        + privateKeyPath.toAbsolutePath()
        );

        System.out.println(
                "Public key location: "
                        + publicKeyPath.toAbsolutePath()
        );

        System.out.println();

        System.out.println(
                "RSA key pair generated successfully."
        );
    }
}