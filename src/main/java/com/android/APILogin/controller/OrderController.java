package com.android.APILogin.controller;

import com.android.APILogin.dto.request.OrderDetailDtoRequest;
import com.android.APILogin.dto.request.OrderDtoRequest;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.enums.OrderStatus;
import com.android.APILogin.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {
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

    @GetMapping("/shop/order")
    public ResponseData<List<OrderDtoRequest>> getOrderDetailByStatusAndPostId(@RequestParam OrderStatus status, @RequestParam Long postId){
        List<OrderDtoRequest> list = orderServiceImpl.getOrderDetailsByStatus(postId,status);
        return new ResponseData<>(HttpStatus.OK.value(), "success", list);
    }

    @PostMapping("/update-order")
    public ResponseData<Void> updateOrderStatus(@RequestParam Long orderId, @RequestParam OrderStatus status) {
        boolean success = orderServiceImpl.updateOrderStatus(orderId, status);
        if (success) {
            return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật trạng thái thành công", null);
        } else {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Không tìm thấy đơn hàng hoặc cập nhật thất bại", null);
        }
    }
}
