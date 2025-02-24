package com.offnine.carten.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.domain.OrderStatus;
import com.offnine.carten.exception.ProductException;
import com.offnine.carten.exception.SellerException;
import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.Seller;
import com.offnine.carten.service.OrderService;
import com.offnine.carten.service.SellerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller/orders")
public class SellerOrderController {
    private final OrderService orderService;
    private SellerService sellerService;

    
     @GetMapping()
     public ResponseEntity<List<Order>> getAllOrdersHandler(
        @RequestHeader("Authorization") String jwt
     ) throws SellerException, ProductException{
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.sellersOrder(seller.getId());
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
        

     }
     @PatchMapping("/{orderId}/status/{orderStatus}")
     public ResponseEntity<Order>  updateOrderHandler(
        @RequestHeader("Authorization") String jwt,
        @PathVariable OrderStatus orderStatus,
        @PathVariable Long orderId

     ) throws Exception{
        Order orders= orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
     }

}
