package com.offnine.carten.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.offnine.carten.Repo.AddressRepo;
import com.offnine.carten.Repo.OrderItemRepo;
import com.offnine.carten.Repo.OrderRepo;
import com.offnine.carten.domain.OrderStatus;
import com.offnine.carten.domain.PaymentStatus;
import com.offnine.carten.modal.Address;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.CartItem;
import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.OrderItem;
import com.offnine.carten.modal.User;
import com.offnine.carten.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{
     private final AddressRepo addressRepo;
     private final OrderRepo orderRepo;
     private final OrderItemRepo orderItemRepo;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
      if(!user.getAddresses().contains(shippingAddress)){
        user.getAddresses().add(shippingAddress);
      }
      Address address = addressRepo.save(shippingAddress);
      // brand 1 ---4 shirt;
      // brand 20--- 3 pant;
      // barand 3 -----1 watch;
      // we must creaste seperated order from different vendor because this is multi vendor eccomerce

      Map<Long,List<CartItem>> itemBYSeller = cart.getCartItem().stream().collect(Collectors.groupingBy(item-> item.getProduct().getSeller().getId()));
      Set<Order> orders = new HashSet<>();
      for(Map.Entry<Long,List<CartItem>> entry :itemBYSeller.entrySet()){
        Long sellerId = entry.getKey();
        List<CartItem> items = entry.getValue();

        int totalOrderPrice = items.stream().mapToInt(
            CartItem :: getSellingPrice
        ).sum();
        int totalItem = items.stream().mapToInt(CartItem ::getQuantity).sum();
        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setSellerId(sellerId);
        createdOrder.setTotalMrpPrice(totalOrderPrice);
        createdOrder.setTotalSellingPrice(totalOrderPrice);
        createdOrder.setTotalItems(totalItem);
        createdOrder.setShippingAddress(shippingAddress);
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.getPaymDetails().setStatus(PaymentStatus.PENDING);
        Order savedOrder= orderRepo.save(createdOrder);
        orders.add(savedOrder);

        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item :items){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            
            orderItem.setMrpPrice(item.getMrpPrice() ==null? 0: item.getMrpPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setSellingPrice(item.getSellingPrice());
            savedOrder.getOrderItems().add(orderItem);
            OrderItem savOrderItem = orderItemRepo.save(orderItem);
             orderItems.add(savOrderItem);
        }
      }
      return orders;
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        return orderRepo.findById(id).orElseThrow(()-> new Exception("Order not found"));
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepo.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepo.findBySellerId(sellerId);

    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepo.save(order);
    }

    @Override
    public Order cancleOrder(Long orderId, User user) throws Exception {
        Order order = findOrderById(orderId);

        if(!user.getId().equals(order.getUser().getId())){
            throw new Exception("You don't have access to this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepo.save(order);
    }

    @Override
    public OrderItem getOrderById(Long id) throws Exception {
        return orderItemRepo.findById(id).orElseThrow(()-> new Exception("Order item not exist ...."));
    }
    
}
