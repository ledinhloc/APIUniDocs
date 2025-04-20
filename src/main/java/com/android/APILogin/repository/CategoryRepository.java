package com.android.APILogin.repository;

import com.android.APILogin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAll();

    Optional<Category> findCategoryByCateId(Long cateId);

    @Query("SELECT DISTINCT c FROM Category c JOIN c.documents d WHERE d.user.userId = :userId")
    List<Category> findByDocumentUserId(@Param("userId") Long userId);
}
