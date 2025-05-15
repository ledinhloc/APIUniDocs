package com.android.APILogin.repository;

import com.android.APILogin.dto.request.OrderDetailDtoRequest;
import com.android.APILogin.entity.Document;
import com.android.APILogin.dto.request.OrderDtoRequest;
import com.android.APILogin.entity.Order;
import com.android.APILogin.entity.User;
import com.android.APILogin.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Order o " +
           "JOIN o.orderDetails od " +
           "WHERE o.user = :user AND o.orderStatus = :status AND od.document = :document")
    boolean existsByUserAndOrderStatusAndOrderDetailsDocument(
            @Param("user") User user,
            @Param("status") OrderStatus status,
            @Param("document") Document document);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Order o " +
           "JOIN o.orderDetails od " +
           "WHERE o.user = :user AND o.orderStatus = :status AND od.document.docId IN :documentIds")
    boolean existsByUserAndOrderStatusAndDocumentIds(
            @Param("user") User user,
            @Param("status") OrderStatus status,
            @Param("documentIds") List<Long> documentIds);

    @Query("SELECT od.document, SUM(od.quantity), o.orderId, o.orderStatus " +
            "FROM OrderDetail od " +
            "JOIN od.order o " +
            "WHERE o.user.userId = :userId AND o.orderStatus = :status " +
            "GROUP BY od.document, o.orderId")
    List<Object[]> findOrderDetailsByUserAndStatus(@Param("userId") Long userId, @Param("status") OrderStatus status);

    @Query("SELECT new com.android.APILogin.dto.request.OrderDetailDtoRequest(" +
            "o.orderId, d.docId, d.docName, u.userId, u.name, u.phone, u.address, o.orderAt, o.orderStatus) " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.document d " +
            "JOIN o.user u " +
            "WHERE o.orderId = :orderId AND d.docId = :docId AND u.userId = :userId")
    OrderDetailDtoRequest findOrderDetailsByOrderId(@Param("orderId") Long orderId, @Param("docId") Long docId, @Param("userId") Long userId);

    @Query("SELECT new com.android.APILogin.dto.request.OrderDtoRequest(" +
            "o.orderId, d.docId, d.docName, d.originalPrice, d.sellPrice, d.docImageUrl, d.docDesc, od.quantity, o.orderStatus) " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.document d " +
            "JOIN o.user u " +
            "WHERE o.orderStatus = :orderStatus AND d.user.userId = :postId")
    List<OrderDtoRequest> findOrderDetailsByOrderStatusAndPostId(
            @Param("orderStatus") OrderStatus orderStatus,
            @Param("postId") Long postId);
}
