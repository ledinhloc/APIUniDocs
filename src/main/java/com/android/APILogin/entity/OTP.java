package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="otp")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long otpId;

    @Column(nullable = false)
    private String otpNum;

    // Hết hạn sau 5 phút
    @Column(nullable = false)
    private LocalDateTime otpExpired;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @Column(nullable = false)
    Boolean isActive = false;
}
