package com.android.APILogin.service;

import com.android.APILogin.entity.Order;
import com.android.APILogin.dto.request.CreateOrderFromCartRequest;

public interface OrderService {
    Order createOrderFromCart(CreateOrderFromCartRequest request);
    void handlePaymentSuccess(Long orderId);
}
