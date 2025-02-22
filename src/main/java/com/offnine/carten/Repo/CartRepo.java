package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.User;

import java.util.List;


public interface CartRepo  extends  JpaRepository<Cart, Long>{
Cart  findByUserId(Long id);
}
