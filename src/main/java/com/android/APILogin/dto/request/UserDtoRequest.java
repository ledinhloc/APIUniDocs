package com.android.APILogin.dto.request;

import com.android.APILogin.enums.AccountType;
import com.android.APILogin.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    private String name;
    private String email;
    private String password;
    private String avatar;
    private Role role;
    private Boolean isActive;
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate dob;
    private AccountType type;

    public UserDtoRequest(String name, String email, String avatar, String phoneNumber, String address, String gender, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
    }
}
