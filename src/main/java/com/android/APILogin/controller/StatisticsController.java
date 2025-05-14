package com.android.APILogin.controller;

import com.android.APILogin.dto.request.MonthlyStatisticsDto;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.service.impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsServiceImpl statisticsService;
    @GetMapping("/monthly/{userId}")
    public ResponseData<List<MonthlyStatisticsDto>> getMonthlyStats(@PathVariable Long userId) {
        List<MonthlyStatisticsDto> monthlyStatisticsDtos = statisticsService.getMonthlyStatistics(userId);
        if(monthlyStatisticsDtos == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(),"Not found",monthlyStatisticsDtos);
        }
        else {
            return new ResponseData<>(HttpStatus.OK.value(),"list report and statistic",monthlyStatisticsDtos);
        }
    }
}
