package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.MonthlyStatisticsDto;
import com.android.APILogin.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl {
    @Autowired
    private OrderRepository orderRepository;
    public List<MonthlyStatisticsDto> getMonthlyStatistics(Long userId) {
        // Lấy dữ liệu từ các query
        List<Map<String, Object>> revenueAndOrders = orderRepository.getMonthlyRevenueAndOrders(userId);
        List<Map<String, Object>> productsSold = orderRepository.getMonthlyProductsSold(userId);
        List<Map<String, Object>> revenueByCategory = orderRepository.getMonthlyRevenueByCategory(userId);

        // Xử lý tổng hợp dữ liệu
        Map<String, MonthlyStatisticsDto> resultMap = new LinkedHashMap<>();

        // Xử lý doanh thu và đơn hàng
        for (Map<String, Object> item : revenueAndOrders) {
            String monthYear = (String) item.get("monthYear");
            MonthlyStatisticsDto dto = new MonthlyStatisticsDto();
            dto.setMonthYear(monthYear);

            // Ép kiểu đúng
            dto.setTotalRevenue(((Number) item.get("totalRevenue")).doubleValue());
            dto.setTotalOrders(((Number) item.get("totalOrders")).longValue());

            resultMap.put(monthYear, dto);
        }

        // Xử lý số sản phẩm bán
        for (Map<String, Object> item : productsSold) {
            String monthYear = (String) item.get("monthYear");
            MonthlyStatisticsDto dto = resultMap.getOrDefault(monthYear, new MonthlyStatisticsDto());

            dto.setTotalProductsSold(((Number) item.get("totalProducts")).longValue());

            resultMap.put(monthYear, dto);
        }

        // Xử lý doanh thu theo loại
        Map<String, Map<String, Double>> categoryRevenueMap = new HashMap<>();
        for (Map<String, Object> item : revenueByCategory) {
            String monthYear = (String) item.get("monthYear");
            String category = (String) item.get("category");

            // Ép kiểu Double
            Double revenue = ((Number) item.get("revenue")).doubleValue();

            categoryRevenueMap
                    .computeIfAbsent(monthYear, k -> new HashMap<>())
                    .put(category, revenue);
        }

        // Gộp dữ liệu vào DTO cuối cùng
        return resultMap.entrySet().stream()
                .map(entry -> {
                    MonthlyStatisticsDto dto = entry.getValue();
                    dto.setRevenueByCategory(categoryRevenueMap.getOrDefault(entry.getKey(), new HashMap<>()));
                    return dto;
                })
                .sorted(Comparator.comparing(MonthlyStatisticsDto::getMonthYear))
                .collect(Collectors.toList());
    }
}
