package com.offnine.carten.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.Repo.CartRepo;
import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.config.JwtProvider;
import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.User;
import com.offnine.carten.reponse.SignUpRequest;
import com.offnine.carten.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

        @Autowired
        private  UserRepo userRepo;
        
        
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private CartRepo cartRepo;

        @Autowired
        private JwtProvider jwtProvider;

    @Override
    public String createUser(SignUpRequest req) {
        User user = userRepo.findByEmail(req.getEmail());
        if(user==null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("9837373");
    createdUser.setPassword(passwordEncoder.encode(req.getOtp()));
     User savedUser = this.userRepo.save(createdUser);
    Cart cart = new Cart();
    cart.setUser(savedUser);
  

        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
        
    }
    
}
