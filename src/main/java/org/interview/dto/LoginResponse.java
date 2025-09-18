package org.interview.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResponse {
    private String accessToken;
    public LoginResponse(String accessToken) { this.accessToken = accessToken; }

}