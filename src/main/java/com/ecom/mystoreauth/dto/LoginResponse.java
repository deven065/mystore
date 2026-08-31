package com.ecom.mystoreauth.dto;

import com.ecom.mystoreauth.entity.Role;

public record LoginResponse(
        String token,
        Long userId,
        String fullName,
        String email,
        Role role
) {
}

/*
React
   ↓
email + password
   ↓
Auth Service
   ↓
verify credentials
   ↓
generate JWT
*/