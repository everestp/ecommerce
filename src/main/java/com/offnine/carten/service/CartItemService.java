package com.offnine.carten.service;

import com.offnine.carten.modal.CartItem;

public interface CartItemService {
    CartItem updCartItem(Long userId,Long cartItemId, CartItem cartItem) throws Exception;
    void removeCartItem(Long userId,Long cartItemId) throws Exception;
    CartItem findCartItemById(Long Id) throws Exception;

}
