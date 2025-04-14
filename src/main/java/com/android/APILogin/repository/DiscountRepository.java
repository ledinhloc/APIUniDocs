package com.android.APILogin.repository;

import com.android.APILogin.dto.request.DiscountDto;
import com.android.APILogin.entity.Discount;
import com.android.APILogin.enums.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    @Query(value="SELECT NEW com.android.APILogin.dto.request.DiscountDto(" +
            "d.discountId, d.discountName, d.discountType, d.status, d.scope, d.usageLimit, d.usedCount, d.discountValue, d.startDate, " +
            "d.maxPrice, d.minPrice, d.endAt, d.scopeId) " +
            "FROM Discount d WHERE d.scope = :scope " +
            "AND (" +
            "(:scope = com.android.APILogin.enums.Scope.SHOP " +
            "AND d.user.userId     IN :userIds)" +
            "OR (:scope = com.android.APILogin.enums.Scope.CATEGORY " +
            "AND d.scopeId     IN :categoryIds)" +
            "OR (:scope = com.android.APILogin.enums.Scope.DOCUMENT " +
            "AND d.scopeId     IN :documentIds)" +
            ") ")
    List<DiscountDto> findDistinctByScopeAndScopeIds(
            @Param("scope") Scope scope,
            @Param("userIds")      List<Long> userIds,
            @Param("categoryIds")  List<Long> categoryIds,
            @Param("documentIds")  List<Long> documentIds);
}
