package com.offnine.carten.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.offnine.carten.Repo.ProductRepo;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.Product;
import com.offnine.carten.modal.User;
import com.offnine.carten.reponse.ApiResponse;
import com.offnine.carten.request.AddItemRequest;
import com.offnine.carten.service.CartItemService;
import com.offnine.carten.service.CartService;
import com.offnine.carten.service.ProductService;
import com.offnine.carten.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

@GetMapping()
public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception{
    User user = userService.findUserByJwtToken(jwt);
    Cart cart =cartService.findUserCart(user);
    



    return new ResponseEntity<Cart>(cart,HttpStatus.OK);
}
@PutMapping("/add")
public ResponseEntity<CartItem> addItemToCart(
    @RequestHeader("Authorization") String jwt,
    @RequestBody AddItemRequest req
) throws Exception{
    User user =userService.findUserByJwtToken(jwt);
    Product product = productService.findProductById(req.getProductId());
    CartItem  item = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());
     ApiResponse res =  new ApiResponse();
    res.setMessage("Item added to Cart Successfully");
    return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
    
   
    
}

@DeleteMapping("/item/{cartItemId}")
public ResponseEntity<ApiResponse> deleteCartItemHandler(
    @PathVariable Long cartItemId,
    @RequestHeader("Authorization") String jwt

) throws Exception{
    User user =userService.findUserByJwtToken(jwt);
   cartItemService.removeCartItem(cartItemId,user.getId());
   ApiResponse res =  new ApiResponse();
    res.setMessage("Item Removed From Cart");
    return new ResponseEntity<ApiResponse>(res,HttpStatus.ACCEPTED);
    
}

@PutMapping("item/{cartItemId}")
public ResponseEntity<CartItem> updateCartItemHandler(
    @PathVariable Long cartItemId, 
    @RequestBody CartItem cartItem,
    @RequestHeader("Authorization") String jwt
    ) throws Exception {
  User user = userService.findUserByJwtToken(jwt);
  CartItem updatedCartItem = null;
  if(cartItem.getQuantity()>0){
    updatedCartItem = cartItemService.updCartItem(user.getId(), cartItemId, cartItem);

  }
    
    return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
}


}
