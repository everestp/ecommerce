package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Wishlist;

public interface WishListRepo  extends JpaRepository<Wishlist,Long>{
    Wishlist findByUserId(Long userId);
    
}
