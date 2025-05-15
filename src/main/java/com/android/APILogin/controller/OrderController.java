package com.android.APILogin.controller;

import com.android.APILogin.dto.request.CreateOrderFromCartRequest;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.entity.Order;
import com.android.APILogin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseData<Long> createOrderFromCart(@RequestBody CreateOrderFromCartRequest request) {
        Order order = orderService.createOrderFromCart(request);
        return new ResponseData<>(HttpStatus.OK.value(),"success",order.getOrderId()) ;
    }

    @PostMapping("/{orderId}/payment-success")
    public ResponseEntity<Void> handlePaymentSuccess(@PathVariable Long orderId) {
        orderService.handlePaymentSuccess(orderId);
        return ResponseEntity.ok().build();
    }
}
