package com.offnine.carten.reponse;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String otp;
}
