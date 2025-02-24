package com.offnine.carten.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.offnine.carten.modal.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem,Long> {
    
}
