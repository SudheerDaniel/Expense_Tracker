package com.example.springbootmvcexample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmvcexample.dto.AuthResponseDTO;
import com.example.springbootmvcexample.model.RefreshToken;
import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.security.JwtUtil;
import com.example.springbootmvcexample.service.RefreshTokenService;
import com.example.springbootmvcexample.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

        @PostMapping("/signup")
        public ResponseEntity<String> signup(@RequestBody User user) {
            userService.signup(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
        
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User user) {
            User existingUser = userService.findByEmail(user.getEmail());
            if (!existingUser.isEmailVerified()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please verify your email before logging in");
            }
            if (passwordEncoder.matches(user.getPassword() , existingUser.getPassword())) {
                String accessToken = jwtUtil.generateToken(existingUser.getEmail());
                String refreshToken = refreshTokenService.createRefreshToken(existingUser.getId(), existingUser.getEmail());
                return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // accepts a refresh token and issues a new access token if the refresh token is valid
        @PostMapping("/refresh")
        public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
            String refreshTokenValue = body.get("refreshToken");
            if (refreshTokenValue == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is required");
            }
   
            Optional<RefreshToken> storedToken = refreshTokenService.validateRefreshToken(refreshTokenValue);
            if (storedToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
            }

            String email = jwtUtil.extractEmail(refreshTokenValue);
            String newAccessToken = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
    
        // logs out the user by deleting their refresh token, so it can no longer be used
        @PostMapping("/logout")
        public ResponseEntity<String> logout(@RequestBody Map<String, String> body) {
            String refreshTokenValue = body.get("refreshToken");
            if (refreshTokenValue != null) {
                String email = jwtUtil.extractEmail(refreshTokenValue);
                User user = userService.findByEmail(email);
                refreshTokenService.deleteByUserId(user.getId());
             }
             return ResponseEntity.ok("Logged out successfully");
        } 

        @GetMapping("/verify")
        public ResponseEntity<String> verify(@RequestParam String token) {
             userService.verifyEmail(token);
             return ResponseEntity.ok("Email verified successfully");
        }
    

}
