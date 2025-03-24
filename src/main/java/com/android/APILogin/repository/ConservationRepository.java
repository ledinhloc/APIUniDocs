package com.android.APILogin.repository;

import com.android.APILogin.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConservationRepository extends JpaRepository<Conversation, Long> {
}
