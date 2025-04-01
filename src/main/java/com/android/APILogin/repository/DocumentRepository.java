package com.android.APILogin.repository;


import com.android.APILogin.entity.Document;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAll();

    @Query(value = "SELECT d FROM Document d " +
            "WHERE " +
            "((:sortType != 'relevance' AND (:keyword IS NULL OR LOWER(d.docName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            " OR " +
            "(:sortType = 'relevance' AND :keyword IS NOT NULL AND LOWER(d.docName) LIKE LOWER(CONCAT('%', :keyword, '%'))))) " +
            "AND (:cateIds IS NULL OR d.category.cateId IN :cateIds) " +
            "AND (:minPrice IS NULL OR d.sellPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR d.sellPrice <= :maxPrice) " +
            "AND (:ratings IS NULL OR ROUND(COALESCE( " +
            "       (SELECT AVG(r.rate) FROM Review r WHERE r.document.docId = d.docId), 0)) IN :ratings) " +
            "ORDER BY " +
            "CASE WHEN :sortType = 'relevance' THEN LOCATE(LOWER(:keyword), LOWER(d.docName)) ELSE 1 END ASC, " +
            "CASE WHEN :sortType = 'newest' THEN d.createdAt END DESC, " +
            "CASE WHEN :sortType = 'most_sold' THEN " +
            "       (SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId) " +
            "    ELSE -1 END DESC, " +
            "CASE WHEN :sortType = 'most_downloaded' THEN d.download ELSE -1 END DESC, " +
            "CASE WHEN :sortType = 'most_viewed' THEN d.view ELSE -1 END DESC, " +
            "CASE WHEN :sortType = 'price_asc' THEN d.sellPrice ELSE NULL END ASC, " +
            "CASE WHEN :sortType = 'price_desc' THEN d.sellPrice ELSE NULL END DESC, " +
            "d.docId ASC")
    List<Document> filterDocument(@Param("keyword") String keyword,
                                  @Param("sortType") String sortType,
                                  @Param("cateIds") List<Long> cateIds,
                                  @Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice,
                                  @Param("ratings") List<Integer> ratings,
                                  @Param("daysAgo") LocalDateTime daysAgo);

    @Query(value = "SELECT * FROM Document WHERE LOWER(doc_name) LIKE concat('%', LOWER(:name), '%') ", nativeQuery = true)
    List<Document> searchDocumentByName(String name);
}
