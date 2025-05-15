package com.android.APILogin.controller;

import com.android.APILogin.dto.request.CreateOrderFromCartRequest;
import com.android.APILogin.dto.request.OrderDetailDtoRequest;
import com.android.APILogin.dto.request.OrderDtoRequest;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.entity.Order;
import com.android.APILogin.service.OrderService;
import lombok.RequiredArgsConstructor;
import com.android.APILogin.enums.OrderStatus;
import com.android.APILogin.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/status")
    public ResponseData<List<OrderDtoRequest>> getOrdersByStatus(@RequestParam Long userId, @RequestParam OrderStatus status)
    {
        List<OrderDtoRequest> orderDtoRequests = orderServiceImpl.getOrderHistoryByStatus(userId, status);
        if(orderDtoRequests.isEmpty()){
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Not found", orderDtoRequests);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "success", orderDtoRequests);
        }
    }

//    @PostMapping("/create")
//    public ResponseData<Long> createOrderFromCart(@RequestBody CreateOrderFromCartRequest request) {
//        Order order = orderService.createOrderFromCart(request);
//        return new ResponseData<>(HttpStatus.OK.value(),"success",order.getOrderId()) ;
//    }

//    @PostMapping("/{orderId}/payment-success")
//    public ResponseEntity<Void> handlePaymentSuccess(@PathVariable Long orderId) {
//        orderService.handlePaymentSuccess(orderId);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/order-detail/{orderId}/{docId}/{userId}")
    public ResponseData<OrderDetailDtoRequest> getOrderDetail(@PathVariable Long orderId, @PathVariable Long docId, @PathVariable Long userId)
    {
        OrderDetailDtoRequest orderDetailDtoRequest = orderServiceImpl.getOrderDetails(orderId,docId,userId);
        if(orderDetailDtoRequest == null){
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Not found", orderDetailDtoRequest);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "success", orderDetailDtoRequest);
        }
    }
}
