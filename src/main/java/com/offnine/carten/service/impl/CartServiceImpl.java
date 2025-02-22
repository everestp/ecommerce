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
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) throws Exception {
        Cart cart = findUserCart(user);
        CartItem isPresent = cartItemRepo.findByCartAndProductAndSize(cart, product, size);
        
        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice  = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);

            // Ensure the cart's item list is initialized
            if (cart.getCartItem() == null) {
                cart.setCartItem(new ArrayList<>());
            }
            cart.getCartItem().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepo.save(cartItem);
        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {  
        Cart cart = cartRepo.findByUserId(user.getId());

        // If the cart doesn't exist, create a new one with an initialized list
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setTotalMrpPrice(0);
            cart.setTotalItem(0);
            cart.setDiscount(0);
            cart.setCartItem(new ArrayList<>());
            cart = cartRepo.save(cart);
        }

        // Ensure the cart items list is not null
        if (cart.getCartItem() == null) {
            cart.setCartItem(new ArrayList<>());
        }

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        // Loop over each CartItem and use 0 if any price is null
        for (CartItem cartItem : cart.getCartItem()) {
            int mrpPrice = (cartItem.getMrpPrice() != null) ? cartItem.getMrpPrice() : 0;
            int sellingPrice = (cartItem.getSellingPrice() != null) ? cartItem.getSellingPrice() : 0;
            int quantity = cartItem.getQuantity(); // assuming quantity is a primitive int

            totalPrice += mrpPrice;
            totalDiscountedPrice += sellingPrice; 
            totalItem += quantity;
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));

        return cartRepo.save(cart);
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice <= 0) {
            return 0;
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }
}
