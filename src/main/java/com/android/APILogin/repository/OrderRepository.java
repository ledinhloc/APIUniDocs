package com.android.APILogin.repository;

import com.android.APILogin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y') as monthYear, " +
            "SUM(o.totalSellPrice) as totalRevenue, " +
            "COUNT(o) as totalOrders " +
            "FROM Order o " +
            "WHERE o.orderStatus = 'DELIVERED' " +
            "AND o.user.userId = :userId " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y')")
    List<Map<String, Object>> getMonthlyRevenueAndOrders(@Param("userId") Long userId);

    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y') as monthYear, " +
            "SUM(od.quantity) as totalProducts " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "WHERE o.orderStatus = 'DELIVERED' " +
            "AND o.user.userId = :userId " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y')")
    List<Map<String, Object>> getMonthlyProductsSold(@Param("userId") Long userId);

    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y') as monthYear, " +
            "c.cateName as category, " +
            "SUM(od.quantity * od.sellPrice) as revenue " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.document d " +
            "JOIN d.category c " +
            "WHERE o.orderStatus = 'DELIVERED' " +
            "AND o.user.userId = :userId " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderAt, '%m/%Y'), c.cateName")
    List<Map<String, Object>> getMonthlyRevenueByCategory(@Param("userId") Long userId);
}
