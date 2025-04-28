package com.android.APILogin.service;

import com.android.APILogin.dto.request.DiscountDto;

import java.util.List;

public interface DiscountService {
    List<DiscountDto> findByScopeAndScopeId(List<Long> userIds, List<Long> categoryIds, List<Long> documentIds);
    Long addDiscount(DiscountDto discountDto, Long userId);

}
