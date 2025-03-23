package com.android.APILogin.repository;


import com.android.APILogin.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAll();

    @Query(value = "SELECT * FROM Document WHERE LOWER(doc_name) LIKE concat('%', LOWER(:name), '%') ", nativeQuery = true)
    List<Document> searchDocumentByName(String name);
}
