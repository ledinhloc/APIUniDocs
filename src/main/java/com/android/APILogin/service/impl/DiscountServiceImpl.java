package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.DiscountDto;
import com.android.APILogin.entity.Discount;
import com.android.APILogin.enums.Scope;
import com.android.APILogin.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiscountServiceImpl {
    @Autowired
    private DiscountRepository discountRepository;

    public List<DiscountDto> findByScopeAndScopeId(List<Long> userIds, List<Long> categoryIds, List<Long> documentIds) {
        Set<DiscountDto> discountSet = new HashSet<>();

        if(userIds != null && !userIds.isEmpty()) {
            List<DiscountDto> shopDiscounts = discountRepository.findDistinctByScopeAndScopeIds(Scope.SHOP,userIds, Collections.emptyList(), Collections.emptyList());
            discountSet.addAll(shopDiscounts);
        }
        if(categoryIds != null && !categoryIds.isEmpty()) {
            List<DiscountDto> categoryDiscounts = discountRepository.findDistinctByScopeAndScopeIds(Scope.CATEGORY,Collections.emptyList(), categoryIds, Collections.emptyList());
            discountSet.addAll(categoryDiscounts);
        }
        if(documentIds != null && !documentIds.isEmpty()) {
            List<DiscountDto> documentDiscounts = discountRepository.findDistinctByScopeAndScopeIds(Scope.DOCUMENT,Collections.emptyList(), Collections.emptyList(), documentIds);
            discountSet.addAll(documentDiscounts);
        }
        return new ArrayList<>(discountSet);
    }

}
