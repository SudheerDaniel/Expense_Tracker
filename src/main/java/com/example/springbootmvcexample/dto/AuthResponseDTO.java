package com.example.springbootmvcexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// returned on login - carries both the short-lived access token and the long-lived refresh token
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String accessToken;
    private String refreshToken;


}
