package com.android.APILogin.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CreateOrderFromCartRequest {
    private List<Long> cartIds;  // Danh sách ID của các item trong giỏ hàng
    private Long discountId;     // ID của discount được áp dụng (có thể null)
    private Long userId;         // ID của user
} 