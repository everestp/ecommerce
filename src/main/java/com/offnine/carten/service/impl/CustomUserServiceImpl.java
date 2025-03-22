package com.offnine.carten.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.SellerRepo;
import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    private static final String SELLER_PREFIX = "seller_";
    @Autowired
    private SellerRepo sellerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      if(username.startsWith(SELLER_PREFIX)){
        String actualUsername = username.substring(SELLER_PREFIX.length());
        Seller seller= sellerRepo.findByEmail(actualUsername);
        if(seller!=null){
            return buildUserDetails(seller.getEmail(),seller.getPassword(),seller.getRole());
            
        }



      }
      else{
        User user = userRepo.findByEmail(username);
        if(user!=null){
            return buildUserDetails(user.getEmail(),user.getPassword(),user.getRole());
        }

      }
      throw new UsernameNotFoundException("User or Seller not found with email" + username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
    if(role==null) role=USER_ROLE.ROLE_CUSTOMER;
    List<GrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(new SimpleGrantedAuthority(role.toString()));
    return new org.springframework.security.core.userdetails.User(email, password, authorityList);

    }
    
} 

