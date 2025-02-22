package com.offnine.carten.request;

import com.offnine.carten.domain.USER_ROLE;

import lombok.Data;

@Data
public class LoginOtpRequest {
   private String email;
   private String otp;
   private USER_ROLE role;
}
