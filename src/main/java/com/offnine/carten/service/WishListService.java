package com.offnine.carten.service;

import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;
import com.offnine.carten.modal.Wishlist;

public interface WishListService {


    Wishlist createWishlist(User user);
    Wishlist getWishlistByUser(User user);
    Wishlist addProductToWishlist(User user,Product product);
    
} 