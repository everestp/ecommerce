package com.offnine.carten.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.offnine.carten.domain.PaymentMethod;
import com.offnine.carten.modal.Address;
import com.offnine.carten.modal.Cart;
import com.offnine.carten.modal.Order;
import com.offnine.carten.modal.OrderItem;
import com.offnine.carten.modal.User;
import com.offnine.carten.reponse.PaymentLinkResponse;
import com.offnine.carten.service.CartService;
import com.offnine.carten.service.OrderService;
import com.offnine.carten.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class OrderController {


    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

@PostMapping()
public ResponseEntity<PaymentLinkResponse> createOrderHandler(
    @RequestBody Address shippindAddress,
    @RequestParam PaymentMethod paymentMethod,
    @RequestHeader("Authorization") String jwt

) throws Exception{
User user = userService.findUserByJwtToken(jwt);
Cart cart =cartService.findUserCart(user);
Set<Order> orders = orderService.createOrder(user, shippindAddress, cart);
PaymentLinkResponse res = new PaymentLinkResponse();
return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
}


@GetMapping("/user")
public ResponseEntity<List<Order>> userOrderHistoryHandler(

    @RequestHeader("Authorization") String jwt

) throws Exception{

    User user = userService.findUserByJwtToken(jwt);
    List<Order> orders = orderService.userOrderHistory(user.getId());
    return new ResponseEntity<>(orders,HttpStatus.ACCEPTED)

}

@GetMapping("/{orderId}")
public ResponseEntity<Order> getOrderById(
    @PathVariable Long orderId,
    @RequestHeader("Authorization") String jwt
) throws Exception{
    User user  = userService.findUserByJwtToken(jwt);
    Order order = orderService.findOrderById(orderId);
    return new ResponseEntity<>(order,HttpStatus.ACCEPTED);

}

@GetMapping("/items/{orderItemId}")
public ResponseEntity<OrderItem> getOrderItemById (
    @PathVariable Long orderItemId ,
    @RequestHeader("Authorization") String jwt 
) throws Exception{

    User user =userService.findUserByJwtToken(jwt);
    OrderItem orderItem = orderService.getOrderItemById(orderItemId);
    
return new ResponseEntity<>(orderItem,HttpStatus.ACCEPTED);
}


@PutMapping("/{orderId}/cancel")
public ResponseEntity<Order> cancelOrder(
    @PathVariable Long orderId,
    @RequestHeader("Authorization") String jwt
) throws Exception{
    User user = userService.findUserByJwtToken(jwt);
    Order order = orderService.findOrderById(orderId);


    
 return ResponseEntity.ok(order);
}


}
