package com.android.APILogin.dto.request;

import com.android.APILogin.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String password;
    private String avatar;
    private LocalDateTime dob;
    private Role role;
    private Boolean isActive;
}
