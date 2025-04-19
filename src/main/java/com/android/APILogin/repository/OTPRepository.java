package com.android.APILogin.repository;

import com.android.APILogin.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findTopByUserEmailOrderByOtpExpiredDesc(String email);
}
