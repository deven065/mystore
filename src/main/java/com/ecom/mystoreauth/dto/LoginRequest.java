package com.ecom.mystoreauth.dto;

public record LoginRequest(
        String email,
        String password
) {
}

/*
 React
  ↓
email + password
  ↓
LoginRequest
  ↓
AuthController
  ↓
AuthService
  ↓
UserRepository
  ↓
PasswordEncoder
  ↓
JWT
*/