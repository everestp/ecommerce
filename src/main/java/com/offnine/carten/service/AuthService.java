package com.offnine.carten.service;

import com.offnine.carten.reponse.SignUpRequest;

public interface AuthService {
    
    String createUser(SignUpRequest req);
}
