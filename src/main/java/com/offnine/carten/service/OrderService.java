package com.offnine.carten.service;

import java.util.List;
import java.util.Set;

import com.offnine.carten.domain.OrderStatus;
import com.offnine.carten.modal.Address;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.OrderItem;
import com.offnine.carten.modal.User;

public interface OrderService {

    Set<Order> createOrder(User user,Address shippingAddress,Cart cart);
    Order findOrderById(Long id) throws Exception;

    List<Order> userOrderHistory (Long userId);
    List<Order> sellersOrder(Long sellerId);
    Order updateOrderStatus(Long orderId,OrderStatus orderStatus) throws Exception;
    Order cancleOrder(Long orderId,User user) throws Exception;
    OrderItem getOrderItemById(Long id) throws Exception;

    
}
