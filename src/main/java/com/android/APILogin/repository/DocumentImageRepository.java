package com.android.APILogin.repository;

import com.android.APILogin.dto.response.DocumentImageDto;
import com.android.APILogin.entity.DocumentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentImageRepository extends JpaRepository<DocumentImage, Long> {
    List<DocumentImage> findAllByDocument_DocId(Long documentDocId);
}
