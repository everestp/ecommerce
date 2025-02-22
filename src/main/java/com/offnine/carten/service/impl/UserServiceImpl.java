package com.offnine.carten.service.impl;



import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.config.JwtProvider;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByEmail(String email) throws Exception {
       User user = userRepo.findByEmail(email);
       
       if(user==null){
        throw new Exception("User not found with email :-"+email);

    }
    return user;
    }
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email =jwtProvider.getEmailFromJwtToken(jwt);
        User user = this.findUserByEmail(email);

        return user;
    }

    
}
