package com.ecom.mystoreauth.service;

import com.ecom.mystoreauth.dto.SignupRequest;
import com.ecom.mystoreauth.entity.Role;
import com.ecom.mystoreauth.entity.User;
import com.ecom.mystoreauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //  Adding Constructor
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(SignupRequest request) {
        //  Check whether both passwords match
        if (!request.password()
                .equals(request.confirmPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        //  Check whether this email is already registered
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already exists");
        }

        //  Create a new User object
        User user = new User();

        //  Copy signup Information from dto to entity
        user.setFullName(request.fullName());
        user.setEmail(request.email());

        //  Hash the password before storing it
        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        //  New users are CUSTOMERS by default
        user.setRole(Role.CUSTOMER);

        //  This account was created using email/password authentication
        user.setProvider("LOCAL");

        //  Local users dont have an OAuth password ID
        user.setProviderId(null);

        //  Save user in database
        userRepository.save(user);
    }
}


/*
 SignupRequest
     ↓
Check passwords
     ↓
Check email
     ↓
Create User
     ↓
Hash password
     ↓
Assign CUSTOMER
     ↓
Set provider = LOCAL
     ↓
Save
     ↓
MySQL
 */