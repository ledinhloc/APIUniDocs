package com.android.APILogin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyStatisticsDto {
    private String monthYear;
    private Double totalRevenue;
    private Long totalOrders;
    private Long totalProductsSold;
    private Map<String, Double> revenueByCategory;
}
