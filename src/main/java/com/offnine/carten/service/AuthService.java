package com.offnine.carten.service;

import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.reponse.AuthResponse;
import com.offnine.carten.reponse.SignUpRequest;
import com.offnine.carten.request.LoginRequest;

public interface AuthService {
    void sentLoginOtp(String email,USER_ROLE role) throws Exception;
    String createUser(SignUpRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
