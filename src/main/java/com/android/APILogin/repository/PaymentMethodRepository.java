package com.android.APILogin.repository;

import com.android.APILogin.entity.Participant;
import com.android.APILogin.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
