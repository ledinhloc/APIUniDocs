package com.android.APILogin.service.impl;

import com.android.APILogin.dto.request.DiscountDto;
import com.android.APILogin.entity.Discount;
import com.android.APILogin.entity.User;
import com.android.APILogin.enums.Scope;
import com.android.APILogin.repository.DiscountRepository;
import com.android.APILogin.repository.UserRepository;
import com.android.APILogin.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final UserRepository userRepository;

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

    @Override
    public Long addDiscount(DiscountDto dto, Long userId) {
        Discount discount = Discount.builder()
                .discountName(dto.getDiscountName())
                .discountType(dto.getDiscountType())
                .status(dto.getStatus())
                .scope(dto.getScope())
                .usageLimit(dto.getUsageLimit())
                .usedCount(0)
                .discountValue(dto.getDiscountValue())
                .startDate(dto.getStartDate())
                .maxPrice(dto.getMaxPrice())
                .minPrice(dto.getMinPrice())
                .endAt(dto.getEndAt())
                .scopeId(dto.getScopeId())
                .build();

        Optional<User> user = userRepository.findById(userId);
        discount.setUser(user.get());
        discountRepository.save(discount);
        return discount.getDiscountId();
    }

}
