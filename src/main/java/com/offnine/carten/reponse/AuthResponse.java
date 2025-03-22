package com.offnine.carten.reponse;

import com.offnine.carten.domain.USER_ROLE;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String messege;
    private  USER_ROLE role;
}
