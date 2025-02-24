package com.offnine.carten.service;

import java.util.List;

import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Coupon;
import com.offnine.carten.modal.User;

public interface CouponService {
    Cart appyCoupon(String code,double orderValue,User user);
    Cart removeCoupon(String code,User user) throws Exception;
    Coupon findCouponById(Long id);
    Coupon createCoupon(Coupon coupon);
    List<Coupon> findAllcoupons();
    void deleteCoupon(Long id);

}
