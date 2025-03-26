package com.android.APILogin.repository;

import com.android.APILogin.entity.ChatLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLineRepository extends JpaRepository<ChatLine, Long> {
}
