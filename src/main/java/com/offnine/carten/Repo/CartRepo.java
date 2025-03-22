package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Cart;

public interface CartRepo  extends  JpaRepository<Cart, Long>{
    
    
}
