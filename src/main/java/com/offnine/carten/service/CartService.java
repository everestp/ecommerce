package com.offnine.carten.service;

import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;

public interface CartService {
    public CartItem addCartItem(
        User user,
        Product product,
        String size,
        int quantity
    ) throws Exception;

    public Cart findUserCart(User user);

    
}
