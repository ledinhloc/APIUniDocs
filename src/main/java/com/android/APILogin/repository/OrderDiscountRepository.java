package com.android.APILogin.repository;

import com.android.APILogin.entity.Order;
import com.android.APILogin.entity.OrderDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDiscountRepository extends JpaRepository<OrderDiscount, Long> {
}
