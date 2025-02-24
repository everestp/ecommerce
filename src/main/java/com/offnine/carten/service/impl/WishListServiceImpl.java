package com.offnine.carten.service.impl;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.WishListRepo;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;
import com.offnine.carten.modal.Wishlist;
import com.offnine.carten.service.WishListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService{
    
    private final WishListRepo wishListRepo;
    @Override
    public Wishlist createWishlist(User user) {
      Wishlist wishlist = new Wishlist();
      wishlist.setUser(user);
      return wishListRepo.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUser(User user) {
        Wishlist wishList= wishListRepo.findByUserId(user.getId());
        if(wishList==null){
            wishList =createWishlist(user);

        }
        return wishList;

    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishList= getWishlistByUser(user);
        if(wishList.getProducts().contains(product)){
            wishList.getProducts().remove(product);
        }
        else{
            wishList.getProducts().add(product);

        }
        return wishListRepo.save(wishList);
    }
    
}
