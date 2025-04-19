package com.android.APILogin.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpRequest {
    private String email;
    private String otp;
    private LocalDateTime otpExpired;
    private Boolean isActive;
}
