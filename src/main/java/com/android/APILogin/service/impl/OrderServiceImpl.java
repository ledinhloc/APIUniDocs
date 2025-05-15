package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.OrderDetailDtoRequest;
import com.android.APILogin.dto.request.OrderDtoRequest;
import com.android.APILogin.entity.Document;
import com.android.APILogin.entity.Order;
import com.android.APILogin.enums.OrderStatus;
import com.android.APILogin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDtoRequest> getOrderHistoryByStatus(Long userId, OrderStatus status) {
        List<Object[]> results = orderRepository.findOrderDetailsByUserAndStatus(userId, status);

        return results.stream().map(result -> {
            Document document = (Document) result[0];
            Long quantity = (Long) result[1];
            Long orderId = (Long) result[2];
            OrderStatus orderStatus = (OrderStatus) result[3];

            return OrderDtoRequest.builder()
                    .orderId(orderId)
                    .docId(document.getDocId())
                    .docName(document.getDocName())
                    .originalPrice(document.getOriginalPrice())
                    .sellPrice(document.getSellPrice())
                    .docImageUrl(document.getDocImageUrl())
                    .docDesc(document.getDocDesc())
                    .quantity(quantity.intValue())
                    .status(orderStatus)
                    .build();
        }).collect(Collectors.toList());
    }

    public OrderDetailDtoRequest getOrderDetails(Long orderId, Long docId, Long userId) {
        return orderRepository.findOrderDetailsByOrderId(orderId,docId,userId);
    }

    public List<OrderDtoRequest> getOrderDetailsByStatus(Long postId, OrderStatus status) {
        List<OrderDtoRequest> results = orderRepository.findOrderDetailsByOrderStatusAndPostId(status, postId);
        return results;
    }


    public boolean updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> orderOtp = orderRepository.findById(orderId);
        if(orderOtp.isEmpty()) {
            return false;
        }
        else{
            Order order = orderOtp.get();
            order.setOrderStatus(status);
            orderRepository.save(order);
            return true;
        }

    }

}
