package com.android.APILogin.controller;

import com.android.APILogin.dto.request.OtpRequest;
import com.android.APILogin.dto.request.PasswordResetRequest;
import com.android.APILogin.entity.User;
import com.android.APILogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        System.out.println(email);
        return userService.forgotPassword(email);
    }

    //xac minh otp de kich hoat tai khoan
    @PostMapping("/verify-otp-for-activation")
    public String verifyOtpForActivation(@RequestBody OtpRequest otpRequest) {
        return userService.verifyOtpForActivation(otpRequest);
    }

    //xac minh otp de doi mk
    @PostMapping("/verify-otp-for-password-reset")
    public String verifyOtpForPasswordReset(@RequestBody PasswordResetRequest request) {
        return userService.verifyOtpForPasswordReset(request.getEmail(), request.getOtp(), request.getNewPassword());
    }
}
