package org.interview.controller;

import org.interview.dto.LoginRequest;
import org.interview.dto.LoginResponse;
import org.interview.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String role = "ROLE_USER";
        if ("admin".equals(request.getUsername()) && "adminpass".equals(request.getPassword())) {
            role = "ROLE_ADMIN";
        } else if (!"user".equals(request.getUsername()) || !"password".equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        String token = jwtUtil.generateToken(request.getUsername(), role);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}