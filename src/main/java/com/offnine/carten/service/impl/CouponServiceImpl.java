package com.offnine.carten.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.CartRepo;
import com.offnine.carten.Repo.CouponRepo;
import com.offnine.carten.Repo.UserRepo;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Coupon;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.CouponService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepo couponRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;


    @Override
    public Cart appyCoupon(String code, double orderValue, User user) throws Exception {

        Coupon coupon = couponRepo.findByCode(code);
        Cart cart = cartRepo.findByUserId(user.getId());
        if(coupon==null){
            throw new Exception("Coupon is not valid");
        }


        if(user.getUsedCoupons().contains(coupon)){
            throw new Exception("Coupon Already used");
        }

        if(orderValue < coupon.getMinimumOrderValue()){
            throw new Exception("Valid for minimum order value" + coupon.getMinimumOrderValue());
        }
       if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getVaidityEndDate())){
        user.getUsedCoupons().add(coupon);
        userRepo.save(user);
        double  discountedPrice = (cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;
        cart.setTotalSellingPrice(cart.getTotalSellingPrice() -discountedPrice);
        cart.setCouponCode(code);
        cartRepo.save(cart);
        return cart;

       }
       throw new Exception("Coupon is not valid");
    }

    @Override
    public Cart removeCoupon(String code, User user) throws Exception {
        Coupon coupon = couponRepo.findByCode(code);
        if(coupon==null){
            throw new Exception("Coupon is not valid");
        }
        Cart cart = cartRepo.findByUserId(user.getId());
        double  discountedPrice = (cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;
        cart.setTotalSellingPrice(cart.getTotalSellingPrice() +discountedPrice);
        cart.setCouponCode(null);
        cartRepo.save(cart);

                return cartRepo.save(cart);


    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepo.findById(id).orElseThrow(()-> new Exception("Coupon not found Exception"));

    }

    @Override
    @PreAuthorize("hasRole =('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {

       return couponRepo.save(coupon);
    }

    @Override
    public List<Coupon> findAllcoupons() {
        return couponRepo.findAll();
    }

    @Override
    @PreAuthorize("hasRole =('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {
       findCouponById(id);
       couponRepo.deleteById(id);
          

    }
    
}
