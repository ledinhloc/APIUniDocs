package com.android.APILogin.repository;

import com.android.APILogin.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Document,Long> {
    List<Document> findAll();

}
