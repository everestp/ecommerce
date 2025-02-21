package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.VerificationCode;
import java.util.List;




public interface  VerificationCodeRepo extends  JpaRepository<VerificationCode,Long>{
VerificationCode findByEmail(String email);
VerificationCode  findByOtp(String otp);
    
}
