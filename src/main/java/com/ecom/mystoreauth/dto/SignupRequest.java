package com.ecom.mystoreauth.dto;

public record SignupRequest(
        String fullName,
        String email,
        String password,
        String confirmPassword
) {
}
