package com.android.APILogin.repository;

import com.android.APILogin.entity.Seen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeenRepository extends JpaRepository<Seen, Long> {
}
