package com.offnine.carten.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Seller;
import com.offnine.carten.domain.AccountStatus;




public interface SellerRepo extends JpaRepository <Seller,Long>{
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus accountStatus);
    
}
