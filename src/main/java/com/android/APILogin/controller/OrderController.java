package com.android.APILogin.controller;

import com.android.APILogin.dto.request.CreateOrderFromCartRequest;
import com.android.APILogin.entity.Order;
import com.android.APILogin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrderFromCart(@RequestBody CreateOrderFromCartRequest request) {
        Order order = orderService.createOrderFromCart(request);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/payment-success")
    public ResponseEntity<Void> handlePaymentSuccess(@PathVariable Long orderId) {
        orderService.handlePaymentSuccess(orderId);
        return ResponseEntity.ok().build();
    }
}
