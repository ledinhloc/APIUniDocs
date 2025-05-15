package com.android.APILogin.dto.request;

import com.android.APILogin.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDtoRequest {
    private Long orderId;
    private Long docId;
    private String docName;
    private Long userId;
    private String name;
    private String phone;
    private String address;
    private LocalDateTime orderAt;
    private OrderStatus orderStatus;
}
