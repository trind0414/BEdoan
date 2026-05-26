package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/api/auth"})
@RestController
public class AuthController {

    @Value("${lab.security.enabled}")
    private boolean isSecurityEnabled;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping({"/login"})
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        List<Map<String, Object>> users;
        if (this.isSecurityEnabled) {
            users = this.jdbcTemplate.queryForList("SELECT * FROM users WHERE username = ? AND password = ?", new Object[]{username, password});
        } else {
            String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            users = this.jdbcTemplate.queryForList(sql);
        }
        if (!users.isEmpty()) {
            Map<String, Object> user = users.get(0);
            String role = (String) user.get("role");
            String message = "Đăng nhập thành công! Xin chào " + String.valueOf(user.get("username")) + " (Quyền: " + role + ")";
            HttpHeaders headers = new HttpHeaders();
            if ("ADMIN".equalsIgnoreCase(role)) {
                headers.setLocation(URI.create("/h2-console"));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
            if ("USER".equalsIgnoreCase(role)) {
                headers.setLocation(URI.create("/upload"));
                return new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu!");
    }
}