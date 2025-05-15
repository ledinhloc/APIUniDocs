package com.android.APILogin.repository;
import com.android.APILogin.dto.request.CartDto;
import com.android.APILogin.entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(value = "SELECT NEW com.android.APILogin.dto.request.CartDto(" +
            "c.cartId, c.quantity, u.userId, d.docId, d.docName, d.sellPrice, d.docImageUrl) " +
            "FROM Cart c " +
            "LEFT JOIN User u ON c.user.userId = u.userId " +
            "LEFT JOIN Document d ON c.document.docId = d.docId " +
            "WHERE u.userId = :userId")
    List<CartDto> getCartByUserId(@Param("userId") Long userId);
    @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId AND c.document.docId = :productId")
    Optional<Cart> findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    List<Cart> findByUser_UserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.userId = :userId")
    void deleteAllByCartUserId(@Param("userId") Long userId);

    List<Cart> findAllByUserUserIdAndDocumentDocId(
            Long userId,
            Long docId
    );
}
