package com.android.APILogin.controller;

import com.android.APILogin.dto.request.*;
import com.android.APILogin.dto.response.ResponseData;
import com.android.APILogin.dto.response.UserResponse;
import com.android.APILogin.entity.OTP;
import com.android.APILogin.entity.User;
import com.android.APILogin.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Tag(name = "User Controller")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
    @PostMapping("/register")
    public ResponseData<OtpRequest>  register(@RequestBody UserDtoRequest userDTO) {
        OtpRequest otpRequest = userService.createUser(userDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "success", otpRequest);
    }

    @Operation(summary = "User login", description = "Allows users to log in using email and password")
    @PostMapping("/login")
    public ResponseData<UserResponse> login(@RequestBody LoginRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", userService.loginUser(request.getEmail(), request.getPassword())) ;
    }

    @Operation(summary = "Forgot password", description = "Sends a password reset request to the user's email")
    @PostMapping("/forgot-password")
    public ResponseData<OtpRequest> forgotPassword(@RequestParam String email, @RequestParam String phoneNumber) {
        System.out.println(email);
        OtpRequest otpRequest = userService.forgotPassword(email,phoneNumber);
        if(otpRequest == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Not found", otpRequest);
        }
        return new ResponseData<>(HttpStatus.OK.value(), "success", otpRequest);
    }

    @Operation(summary = "Verify OTP for account activation",
            description = "Users enter an OTP code to activate their account after registration")
    @PostMapping("/verify-otp-for-activation")
    public ResponseData<String> verifyOtpForActivation(@RequestBody OtpRequest otpRequest) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", userService.verifyOtpForActivation(otpRequest));
    }

    @Operation(summary = "Verify OTP for password reset",
            description = "Users enter an OTP code to reset their password")
    @PostMapping("/verify-otp-for-password-reset")
    public ResponseData<String> verifyOtpForPasswordReset(@RequestBody PasswordResetRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "success", userService.verifyOtpForPasswordReset(request));
    }

    @GetMapping("/shop-detail/{id}")
    public ResponseData<UserInfoDto> getShopInfo(@PathVariable Long id) {
        UserInfoDto userInfoDto = userService.getUserInfoByDocId(id);
        if(userInfoDto == null) {
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Not found", userInfoDto);
        }
        else{
            return new ResponseData<>(HttpStatus.OK.value(), "success", userInfoDto);
        }
    }

    @GetMapping("/check-email")
    public ResponseData<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.isEmailExist(email);

        if(exists) {
            return new ResponseData<>(HttpStatus.OK.value(), "Email exist", exists);
        }
        else{
            return new ResponseData<>(HttpStatus.NOT_FOUND.value(), "Email not exist", exists);
        }
    }

    @PostMapping("/check-otp")
    public ResponseData<String> checkOtp(@RequestBody OtpRequest otpRequest) {
        return new ResponseData<>(HttpStatus.OK.value(), "Email exist", userService.checkOtp(otpRequest));
    }
}
