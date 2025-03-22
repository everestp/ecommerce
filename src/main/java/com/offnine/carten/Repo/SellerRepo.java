package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Seller;



public interface SellerRepo extends JpaRepository <Seller,Long>{
    Seller findByEmail(String email);
    
}
