package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.Order;
import java.util.List;


public interface OrderRepo extends JpaRepository<Order ,Long> {
    List<Order> findBySellerId(Long sellerId);
    List<Order> findByUserId(Long userId);
    
}
