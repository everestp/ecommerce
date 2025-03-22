package com.offnine.carten.service;

import com.offnine.carten.reponse.SignUpRequest;

public interface AuthService {
    void sentLoginOtp(String email) throws Exception;
    String createUser(SignUpRequest req) throws Exception;
}
