package com.android.APILogin.repository;


import com.android.APILogin.dto.response.DocumentDto;
import com.android.APILogin.entity.Document;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.docName, d.docImageUrl, d.sellPrice, d.originalPrice, " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId) " +
            ") " +
            "FROM Document d " +
            "WHERE d.user.userId <> :userId")
    List<DocumentDto> findAllDoc(@Param("userId") Long userId);

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
                                  @Param("cateIds") Long[] cateIds,
                                  @Param("minPrice") Double minPrice,
                                  @Param("maxPrice") Double maxPrice,
                                  @Param("ratings") Integer[] ratings,
                                  @Param("daysAgo") LocalDateTime daysAgo);

    @Query(value = "SELECT * FROM Document WHERE LOWER(doc_name) LIKE concat('%', LOWER(:name), '%') ", nativeQuery = true)
    List<Document> searchDocumentByName(String name);

    @Query(value = "SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.docName, d.docImageUrl, d.sellPrice, d.originalPrice, d.docPage, " +
            "d.view, d.download, d.docDesc, d.type, d.createdAt, d.maxQuantity, d.user.userId, d.category.cateId, " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId), " +
            "(SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.document.docId = d.docId), " +
            "(SELECT COUNT(r) FROM Review r WHERE r.document.docId = d.docId)" +
            ") " +
            "FROM Document d " +
            "WHERE d.docId = :id")
    Optional<DocumentDto> findAllDocumentDetail(@Param("id") Long id);

    @Query("SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "  d.docId, d.docName, d.docImageUrl, d.sellPrice, " +
            "  (SELECT COALESCE(SUM(od.quantity), 0) " +
            "     FROM OrderDetail od " +
            "    WHERE od.document.docId = d.docId)" +
            ") " +
            "FROM Document d " +
            "WHERE ( :type = 'user' AND d.user.userId    = :id AND d.docId <> :docId) " +
            "   OR ( :type = 'cate' AND d.category.cateId = :id AND d.docId <> :docId)")
    List<DocumentDto> findDocByTypeAndId(@Param("type") String type, @Param("id")   Long   id, @Param("docId") Long   docId);

    @Query("SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.user.userId, d.category.cateId) " +
            "FROM Document d WHERE d.docId IN :docIds")
    List<DocumentDto> findUserAndCateByDocIds(@Param("docIds") List<Long> docIds);

    @Query("SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.docName, d.docImageUrl, d.sellPrice, d.user.userId, " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId), " +
            "(SELECT COALESCE(AVG(r.rate), 0) FROM Review r WHERE r.document.docId = d.docId)" +
            ") " +
            "FROM Document d " +
            "WHERE d.user.userId = :userId")
    List<DocumentDto> findByUserWithLimit(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.docName, d.docImageUrl, d.sellPrice, " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId)) " +
            "FROM Document d " +
            "WHERE d.user.userId = :userId ")
    List<DocumentDto> findAllDocumentByUserId(@Param("userId") Long userId);

    @Query("SELECT new com.android.APILogin.dto.response.DocumentDto(" +
            "d.docId, d.docName, d.docImageUrl, d.sellPrice, " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId)) " +
            "FROM Document d " +
            "WHERE d.user.userId = :userId " +
            "ORDER BY " +
            "CASE WHEN :sortType = 'newest' THEN d.createdAt END DESC, " +
            "CASE WHEN :sortType = 'most_sold' THEN " +
            "(SELECT COALESCE(SUM(od.quantity), 0) FROM OrderDetail od WHERE od.document.docId = d.docId) " +
            "ELSE -1 END DESC, " +
            "CASE WHEN :sortType = 'price_asc' THEN d.sellPrice ELSE NULL END ASC, " +
            "CASE WHEN :sortType = 'price_desc' THEN d.sellPrice ELSE NULL END DESC, " +
            "d.docId ASC")
    List<DocumentDto> findByUserAndTypeSort(@Param("userId") Long userId, @Param("sortType") String sortType);
}
