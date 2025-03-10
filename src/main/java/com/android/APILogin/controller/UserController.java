package com.android.APILogin.controller;

import com.android.APILogin.dto.request.OtpRequest;
import com.android.APILogin.dto.request.PasswordResetRequest;
import com.android.APILogin.dto.request.UserDtoRequest;
import com.android.APILogin.entity.User;
import com.android.APILogin.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Tag(name = "User Controller")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
    @PostMapping("/register")
    public String register(@RequestBody UserDtoRequest userDTO) {
        return userService.createUser(userDTO);
    }

    @Operation(summary = "User login", description = "Allows users to log in using email and password")
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    @Operation(summary = "Forgot password", description = "Sends a password reset request to the user's email")
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        System.out.println(email);
        return userService.forgotPassword(email);
    }

    @Operation(summary = "Verify OTP for account activation",
            description = "Users enter an OTP code to activate their account after registration")
    @PostMapping("/verify-otp-for-activation")
    public String verifyOtpForActivation(@RequestBody OtpRequest otpRequest) {
        return userService.verifyOtpForActivation(otpRequest);
    }

    @Operation(summary = "Verify OTP for password reset",
            description = "Users enter an OTP code to reset their password")
    @PostMapping("/verify-otp-for-password-reset")
    public String verifyOtpForPasswordReset(@RequestBody PasswordResetRequest request) {
        return userService.verifyOtpForPasswordReset(request.getEmail(), request.getOtp(), request.getNewPassword());
    }
}
