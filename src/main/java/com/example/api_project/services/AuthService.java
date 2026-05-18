package com.example.api_project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import com.example.api_project.Repository.RefreshTokenRepository;
import com.example.api_project.Repository.UserRepository;
import com.example.api_project.dto.request.SignupRequest;
import com.example.api_project.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping
    public ResponseEntity<?> signup (@RequestBody SignupRequest signupRequest, HttpServletRequest request){
        
    } 
}
