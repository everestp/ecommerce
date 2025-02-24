package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Coupon;

public interface CouponRepo extends JpaRepository<Coupon,Long> {
    Coupon findByCode(String code);
    
}
