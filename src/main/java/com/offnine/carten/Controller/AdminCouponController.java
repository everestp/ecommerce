package com.offnine.carten.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.Repo.CouponRepo;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Coupon;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.CartService;
import com.offnine.carten.service.CouponService;
import com.offnine.carten.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class AdminCouponController {
    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;
    private final CouponRepo couponRepo;



@PostMapping("/apply")
public ResponseEntity<Cart> applyCoupon(
    @RequestParam String apply,
    @RequestParam String code,
    @RequestParam double orderValue,
    @RequestHeader("Authorization") String jwt
    
){
    User user = userService.findUserByJwtToken(jwt);
    Cart cart;
    if(apply.equals("true")){
        cart= couponService.appyCoupon(code,orderValue , user)
    }
    else{
        cart = couponService.removeCoupon(code, user);
    }
    return ResponseEntity.ok(cart);
}

@PostMapping("/admin/coupon")
public ResponseEntity<Coupon> postMethodName(@RequestBody Coupon coupon) {
  Coupon createdCoupon = couponService.createCoupon(coupon);
  return ResponseEntity.ok(createdCoupon);
}

@DeleteMapping("/admin/delete/{id}")
public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception{
    couponService.deleteCoupon(id);
    return ResponseEntity.ok("Coupon  is delete Sucessfully");
}

@GetMapping("/admin/all")
public ResponseEntity<List<Coupon>> getAllCoupon() {
List<Coupon> coupons = couponService.findAllcoupons();

   return ResponseEntity.ok(coupons);

}


}
