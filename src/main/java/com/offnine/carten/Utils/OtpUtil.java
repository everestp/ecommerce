package com.offnine.carten.Utils;

import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class OtpUtil {
       public static String generateOtp(){
        int otpLength =6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder(otpLength);
        for(int i =0;i<otpLength;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
