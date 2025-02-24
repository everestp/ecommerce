package com.offnine.carten.service.impl;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.CartItemRepo;
import com.offnine.carten.Repo.CartRepo;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.CartItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    
    @Override
    public CartItem updCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
       CartItem item = findCartItemById(id);
       User cartItemUser = item.getCart().getUser();
       
       if(cartItemUser.getId().equals(userId)){
        item.setQuantity(cartItem.getQuantity());
        item.setMrpPrice(item.getQuantity() *item.getProduct().getMrpPrice());
        item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
        return cartItemRepo.save(item);
       }
     throw new Exception("You cannot update cart Item");  
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);
        User cartItemUser = item.getCart().getUser();
        if(cartItemUser.getId().equals(userId)){
            cartItemRepo.delete(item);

        }
       else throw new Exception("You cannot delete cart Item");  
    }

    @Override
    public CartItem findCartItemById(Long Id) throws Exception {
        return cartItemRepo.findById(Id).orElseThrow(()-> new Exception("Cart Item not Found"));
    }
    
}
