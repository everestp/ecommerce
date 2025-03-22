package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.VerificationCode;



public interface  VerificationCodeRepo extends  JpaRepository<VerificationCode,Long>{
VerificationCode findByEmail(String email);
    
}
