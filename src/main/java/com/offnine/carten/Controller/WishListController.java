package com.offnine.carten.Controller;

import java.net.ResponseCache;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.exception.ProductException;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;
import com.offnine.carten.modal.Wishlist;
import com.offnine.carten.service.ProductService;
import com.offnine.carten.service.UserService;
import com.offnine.carten.service.WishListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart/wishList")
public class WishListController {
    
    private final WishListService wishListService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<Wishlist> getWishListByUserId(
        @RequestHeader("Authorization") String jwt

    ) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishListService.getWishlistByUser(user);
        return ResponseEntity.ok(wishlist);


    }

@PostMapping("/add-product/{productId}")
public ResponseEntity<Wishlist> addProductToWishList(
    @PathVariable Long ProductId,
    @RequestHeader("Authorizaation") String jwt
) throws Exception{
    Product product  = productService.findProductById(ProductId);
    User user = userService.findUserByJwtToken(jwt);
    Wishlist updatedWishlist = wishListService.addProductToWishlist(user, product);
    return ResponseEntity.ok(updatedWishlist);
    
    
}
}
