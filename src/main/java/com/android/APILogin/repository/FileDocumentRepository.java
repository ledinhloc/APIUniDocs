package com.android.APILogin.repository;
import com.android.APILogin.dto.response.FileDtoResponse;
import com.android.APILogin.entity.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileDocumentRepository extends JpaRepository<FileDocument, Long> {
    @Query("SELECT f " +
            "FROM FileDocument f " +
            "INNER JOIN Document d ON f.document.docId = d.docId " +
            "WHERE f.document.docId = :docId")
    Optional<FileDocument> findFileDtoByDocId(@Param("docId") Long docId);
}
