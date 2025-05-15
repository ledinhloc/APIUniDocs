package com.android.APILogin.dto.request;

import com.android.APILogin.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoRequest {
    private Long orderId;
    private Long docId;
    private String docName;
    private Double originalPrice;
    private Double sellPrice;
    private String docImageUrl;
    private String docDesc;
    private int quantity;
    private OrderStatus status;
}
