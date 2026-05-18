package com.example.api_project.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.api_project.Models.RefreshToken;
import com.example.api_project.Models.User;
import com.example.api_project.Repository.BlacklistTokenRepository;
import com.example.api_project.Repository.RefreshTokenRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtils {
    private final SecretKey key;
    private final JwtParser jwtParser;

    @Value("${jwt.accessToken.expiration}")
    private long accessTokenExpirationMs;

    @Value("${jwt.refreshToken.expiration}")
    private long refreshTokenExpirationDay;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    // constructor xử lí secret key
    public JwtUtils(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    // Tạo access token
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        // lưu ID và Roles vào claims để sau này lấy ra dùng luôn đỡ phải query DB mỗi request
        claims.put(
            "roles",
            user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .toList()
        );
        claims.put("userId", user.getId()); 
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername()) 
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
 
    // tạo refresh token và lưu vào db
    public RefreshToken generateRefreshToken(User user) {
        // xoá token cũ (nếu có)
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        LocalDateTime timeNow = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        
        RefreshToken newToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString()) // Tạo chuỗi ngẫu nhiên không đoán được
                .lastUsedAt(timeNow)
                .expiresAt(timeNow.plusDays(refreshTokenExpirationDay))
                .build();

        return refreshTokenRepository.save(newToken);
    }

    // xác thực token
    public boolean validateToken (String token){
        try {
            if (blacklistTokenRepository.existsByToken(token)) {
                return false;
            }
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    } 

    // lấy username từ token
    public String getUsernameFromToken (String token){
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    // lấy id user từ token
    public Integer getUserIdFromToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        Object userIdValue = claims.get("userId");
    
        if (userIdValue == null) {
            throw new IllegalArgumentException("Token does not contain userId");
        }
    
        return Integer.valueOf(userIdValue.toString());
}
}
