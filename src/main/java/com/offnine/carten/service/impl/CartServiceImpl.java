package com.offnine.carten.service.impl;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.CartItemRepo;
import com.offnine.carten.Repo.CartRepo;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.CartService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws Exception {
       Cart cart = findUserCart(user);
     CartItem isPrsent = cartItemRepo.findByCartAndProductAndSize(cart, product, size);
     if(isPrsent==null){
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUserId(user.getId());
        cartItem.setSize(size);

        int totalPrice  = quantity * product.getSellingPrice();
        cartItem.setSellingPrice(totalPrice);
        cartItem.setMrpPrice(quantity *product.getMrpPrice());
        cart.getCartItem().add(cartItem);
        cartItem.setCart(cart);
   
        

       return  cartItemRepo.save(cartItem);

     }
     return isPrsent;
    }

    @Override
    public Cart findUserCart(User user) {  //9.15
       Cart cart =cartRepo.findByUserId(user.getId());
       
        int totalPrice =0;
        int totalDiscountedPrice=0;
        int totalItem =0;
        
        for(CartItem cartItem : cart.getCartItem()){

            if(cartItem.getMrpPrice()==null){
                cartItem.setMrpPrice(0);
            }
         totalPrice += cartItem.getMrpPrice();
         totalDiscountedPrice +=cartItem.getSellingPrice(); 
         totalItem +=cartItem.getQuantity();
 
        }
       
      cart.setUser(user);
       cart.setTotalMrpPrice(totalPrice);
       cart.setTotalItem(totalItem);
       cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));
       return cartRepo.save(cart);
     

      
    }
    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <=0){
            return 0;
        }
        double discount = mrpPrice-sellingPrice;
        double discountPercentage =(discount/mrpPrice) *100;
        return (int) discountPercentage;
     }
 
}
