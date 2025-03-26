package com.android.APILogin.repository;

import com.android.APILogin.entity.ChatLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLineRepository extends JpaRepository<ChatLine, Long> {
    List<ChatLine> findByConversationConId(Long conId);
}
