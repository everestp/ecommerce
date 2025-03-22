package com.offnine.carten.service;

import com.offnine.carten.modal.User;

public interface UserService {
    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
