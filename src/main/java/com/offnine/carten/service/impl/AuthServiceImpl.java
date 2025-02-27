package com.offnine.carten.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import com.offnine.carten.Repo.CartRepo;
import com.offnine.carten.Repo.SellerRepo;
import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.Repo.VerificationCodeRepo;
import com.offnine.carten.Utils.OtpUtil;
import com.offnine.carten.config.JwtProvider;
import com.offnine.carten.domain.USER_ROLE;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.modal.User;
import com.offnine.carten.modal.VerificationCode;
import com.offnine.carten.reponse.AuthResponse;
import com.offnine.carten.reponse.SignUpRequest;
import com.offnine.carten.request.LoginRequest;
import com.offnine.carten.service.AuthService;
import com.offnine.carten.service.EmailService;

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
        


        
        private final  VerificationCodeRepo verificationCodeRepo;
        private final EmailService emailService;
        private final CustomUserServiceImpl customUserService;
        private final SellerRepo sellerRepo;
       
    @Override
    public String createUser(SignUpRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepo.findByEmail(req.getEmail());
        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Wrong OTP.....");
        }
       

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

    @Override
    public void sentLoginOtp(String email,USER_ROLE role) throws Exception {
        String SIGNING_PREFIX ="signing_";
        //  String SELLER_PREFIX = "seller_";
        if(email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());
            if(role.equals(USER_ROLE.ROLE_SELLER)){

                Seller seller =sellerRepo.findByEmail(email);
                if(seller== null){
                    throw new Exception("Seller not found with email :-"+email);
                }


            }
            else{
                
                User user =userRepo.findByEmail(email);
                if(user==null){
                    throw new Exception("user not fouund with email :-"+email);
                }


            }

        
        }
        VerificationCode isExist = verificationCodeRepo.findByEmail(email);
        if(isExist!=null){
            verificationCodeRepo.delete(isExist);
        }
        String otp =OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
         verificationCodeRepo.save(verificationCode);
         String  subject ="Carten -The Ecommerce App";
         String text = "Your login/signup OTP: " + otp;
        emailService.sendVerficationOtpEmail(email, otp, subject, text);



    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {
        String userName = req.getEmail();
        String otp = req.getOtp();
        Authentication authentication = authentication(userName,otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessege("Login Successfull");


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(roleName));

                return authResponse;

    }
    private Authentication authentication(String userName, String otp) throws Exception{
        String SELLER_PREFIX = "seller_";
       UserDetails userDetails = customUserService.loadUserByUsername(userName);
       if(userName.startsWith(SELLER_PREFIX)){
        userName = userName.substring(SELLER_PREFIX.length());

       }

       if(userDetails == null){
        throw new Exception("Invalid username");
       }
       VerificationCode verificationCode = verificationCodeRepo.findByEmail(userName);
       if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
        throw new Exception("Wrong OTP");
       }
       return  new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

    
    }
    
}
