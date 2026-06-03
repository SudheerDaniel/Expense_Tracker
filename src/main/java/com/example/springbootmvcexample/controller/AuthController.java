package com.example.springbootmvcexample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootmvcexample.model.User;
import com.example.springbootmvcexample.security.JwtUtil;
import com.example.springbootmvcexample.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

        @PostMapping("/signup")
        public ResponseEntity<String> signup(@RequestBody User user) {
            userService.signup(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }
        
        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody User user) {
            User existingUser = userService.findByEmail(user.getEmail());
            if (passwordEncoder.matches(user.getPassword() , existingUser.getPassword())) {
                String token = jwtUtil.generateToken(existingUser.getEmail());
                return ResponseEntity.ok(token);
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        
    

}
