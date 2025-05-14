package com.android.APILogin.repository;

import com.android.APILogin.entity.Order;
import com.android.APILogin.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
