package com.android.APILogin.service.impl;

import com.android.APILogin.entity.*;
import com.android.APILogin.enums.DiscountStatus;
import com.android.APILogin.enums.OrderStatus;
import com.android.APILogin.repository.*;
import com.android.APILogin.service.OrderService;
import com.android.APILogin.dto.request.CreateOrderFromCartRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service xử lý các thao tác liên quan đến Order
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDiscountRepository orderDiscountRepository;
    private final DocumentRepository documentRepository;
    private final DiscountRepository discountRepository;
    private final CartRepository cartRepository;

    /**
     * Tạo đơn hàng từ giỏ hàng
     * @param request Thông tin tạo đơn hàng (danh sách cart items, discount, user)
     * @return Order đã được tạo
     */
    @Override
    @Transactional
    public Order createOrderFromCart(CreateOrderFromCartRequest request) {
        // 1. Kiểm tra và lấy thông tin user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Lấy thông tin các item trong giỏ hàng
        List<Cart> cartItems = cartRepository.findAllById(request.getCartIds());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 3. Tạo order mới với trạng thái PENDING
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderAt(LocalDateTime.now());

        // Set payment method mặc định là id 4
        PaymentMethod defaultPaymentMethod = paymentMethodRepository.findById(4L)
                .orElseThrow(() -> new RuntimeException("Default payment method not found"));
        order.setPaymentMethod(defaultPaymentMethod);

        // 4. Tính toán tổng tiền và tạo order details
        double totalOriginalPrice = 0;
        double totalSellPrice = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            Document document = cartItem.getDocument();
            
            // Kiểm tra số lượng tồn kho trước khi tạo đơn hàng
            if (document.getMaxQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient quantity for document: " + document.getDocName());
            }

            // Tạo order detail cho từng item trong giỏ hàng
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .document(document)
                    .quantity(cartItem.getQuantity())
                    .originalPrice(document.getOriginalPrice())
                    .sellPrice(document.getSellPrice())
                    .build();

            orderDetails.add(orderDetail);
            
            // Cập nhật tổng tiền gốc và tổng tiền bán
            totalOriginalPrice += document.getOriginalPrice() * cartItem.getQuantity();
            totalSellPrice += document.getSellPrice() * cartItem.getQuantity();
        }

        // Lưu tổng giá ban đầu
        order.setTotalOriginalPrice(totalOriginalPrice);
        // Lưu tổng giá bán (sẽ được cập nhật nếu có discount)
        order.setTotalSellPrice(totalSellPrice);

        // 5. Xử lý discount nếu có
        Long discountId = request.getDiscountId();
        if (discountId != null && discountId > 0) {
            Discount discount = discountRepository.findById(discountId)
                    .orElseThrow(() -> new RuntimeException("Discount not found"));

            // Kiểm tra các điều kiện áp dụng discount
            if (discount.getStatus() != DiscountStatus.ACTIVE) {
                throw new RuntimeException("Discount is not active");
            }
            if (discount.getUsedCount() >= discount.getUsageLimit()) {
                throw new RuntimeException("Discount has reached usage limit");
            }
            if (totalSellPrice < discount.getMinPrice()) {
                throw new RuntimeException("Order total does not meet minimum price requirement for discount");
            }

            // Tính toán giá trị giảm giá dựa trên loại discount
            double discountAmount = calculateDiscountAmount(discount, totalSellPrice);
            
            // Tạo order discount để lưu thông tin giảm giá
            OrderDiscount orderDiscount = OrderDiscount.builder()
                    .order(order)
                    .discount(discount)
                    .discountAmount(discountAmount)
                    .build();

            orderDiscountRepository.save(orderDiscount);
            
            // Cập nhật tổng tiền sau khi áp dụng discount
            totalSellPrice -= discountAmount;
            order.setTotalSellPrice(totalSellPrice);
        }

        // 6. Lưu order và order details vào database
        order = orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetails);

        return order;
    }

    /**
     * Xử lý khi thanh toán thành công
     * @param orderId ID của đơn hàng
     */
    @Override
    @Transactional
    public void handlePaymentSuccess(Long orderId) {
        // 1. Lấy thông tin đơn hàng
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Cập nhật trạng thái đơn hàng thành CONFIRMED
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        // 3. Cập nhật số lượng tồn kho của các document
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            Document document = orderDetail.getDocument();
            document.setMaxQuantity(document.getMaxQuantity() - orderDetail.getQuantity());
            documentRepository.save(document);
        }

        // 4. Cập nhật số lần sử dụng của discount
        if (!order.getOrderDiscounts().isEmpty()) {
            for (OrderDiscount orderDiscount : order.getOrderDiscounts()) {
                Discount discount = orderDiscount.getDiscount();
                if (discount != null) {
                    discount.setUsedCount(discount.getUsedCount() + 1);
                    discountRepository.save(discount);
                }
            }
        }

        // 5. Xóa các item trong giỏ hàng sau khi thanh toán thành công
        List<Long> cartIds = order.getOrderDetails().stream()
                .flatMap(orderDetail ->
                        cartRepository
                                .findAllByUserUserIdAndDocumentDocId(
                                        order.getUser().getUserId(),
                                        orderDetail.getDocument().getDocId()
                                )
                                .stream()
                                .map(Cart::getCartId)
                )
                .toList();

        // Xóa các cart items nếu tìm thấy
        if (!cartIds.isEmpty()) {
            cartRepository.deleteAllById(cartIds);
        }
    }

    /**
     * Tính toán giá trị giảm giá dựa trên loại discount
     * @param discount Thông tin discount
     * @param totalPrice Tổng tiền trước khi giảm giá
     * @return Giá trị giảm giá
     */
    private double calculateDiscountAmount(Discount discount, double totalPrice) {
        double discountAmount = 0;
        
        // Tính giá trị giảm giá dựa trên loại discount
        switch (discount.getDiscountType()) {
            case PERCENT:
                // Giảm giá theo phần trăm
                discountAmount = totalPrice * (discount.getDiscountValue() / 100);
                break;
            case FIXED:
                // Giảm giá cố định
                discountAmount = discount.getDiscountValue();
                break;
        }
        
        // Kiểm tra và giới hạn giá trị giảm giá tối đa nếu có
        if (discount.getMaxPrice() != null && discountAmount > discount.getMaxPrice()) {
            discountAmount = discount.getMaxPrice();
        }
        
        return discountAmount;
    }
}
