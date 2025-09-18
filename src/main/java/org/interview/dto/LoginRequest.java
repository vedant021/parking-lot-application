package org.interview.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    private long serialVersionUID = 1L;
    private String username;
    private String password;
}