package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.Product;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    CartItem findByCartAndProductAndSize(Cart cart,Product product,String size);
    
}
