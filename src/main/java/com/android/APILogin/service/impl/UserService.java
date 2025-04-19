package com.android.APILogin.service.impl;

import com.android.APILogin.dto.mapper.UserMapper;
import com.android.APILogin.dto.request.OtpRequest;
import com.android.APILogin.dto.request.UserDtoRequest;
import com.android.APILogin.dto.request.UserInfoDto;
import com.android.APILogin.dto.response.UserResponse;
import com.android.APILogin.entity.OTP;
import com.android.APILogin.entity.User;
import com.android.APILogin.enums.AccountType;
import com.android.APILogin.enums.UserStatus;
import com.android.APILogin.repository.OTPRepository;
import com.android.APILogin.repository.UserRepository;
import jakarta.persistence.AccessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public UserResponse loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        if(! passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        if(! user.getIsActive()){
            return null;
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUserId(user.getUserId());
        userResponse.setName(user.getName());
        userResponse.setAvatar(user.getAvatar());

        return userResponse;
    }

    public OtpRequest createUser(UserDtoRequest userDTO) {
        User newUser = UserMapper.INSTANCE.toEntity(userDTO);
        newUser.setAddress("");
        newUser.setPhone("");
        newUser.setDob(LocalDate.of(2000,1,1));
        newUser.setStatus(UserStatus.OFFLINE);
        newUser.setLastOnline(LocalDateTime.now());

        if (newUser.getOtps() == null) {
            newUser.setOtps(new ArrayList<>());
        }

        // Tạo OTP cho User
        OTP otp = OTP.builder()
                .otpNum(generateOtp())
                .otpExpired(LocalDateTime.now().plusMinutes(5)) // OTP hết hạn sau 5 phút
                .user(newUser)
                .isActive(true)
                .build();
        newUser.getOtps().add(otp);

        userRepository.save(newUser);
        sendOtp(newUser.getEmail(), otp.getOtpNum());

        OtpRequest otpRequest = new OtpRequest(newUser.getEmail(), otp.getOtpNum(), otp.getOtpExpired(), otp.getIsActive());
        return  otpRequest;

    }

    public String verifyOtpForActivation(OtpRequest otpRequest) {
        Optional<User> userOpt = userRepository.findByEmail(otpRequest.getEmail());
        if(userOpt.isEmpty()) {
            return "User is not found";
        }

        Optional<OTP> latestOtp = otpRepository.findTopByUserEmailOrderByOtpExpiredDesc(otpRequest.getEmail());
        if (latestOtp.isEmpty()) {
            return "No OTP generated for this user";
        }

        OTP otp = latestOtp.get();

        if(otp.getOtpExpired().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(!otp.getOtpNum().equals(otpRequest.getOtp())) {
            return "Invalid otp";
        }

        User user = userOpt.get();
        user.setIsActive(true);
        userRepository.save(user);

        return "User activated successfully!";
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(100000,1000000);
        return String.valueOf(otp);
    }

    public boolean sendOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Code for Account - App UniDocs");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
        return true;
    };

    public String forgotPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        //tạo otp
        User user = userOpt.get();
        OTP otp = OTP.builder()
                .otpNum(generateOtp())
                .otpExpired(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        user.setOtps(List.of(otp));

        sendOtp(user.getEmail(), otp.getOtpNum());
        userRepository.save(user);
        return "Otp sent for reset password";
    }

    public String verifyOtpForPasswordReset(String email, String otp, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        OTP otpEntity = user.getOtps().get(0);

        if(otpEntity.getOtpExpired().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(! otpEntity.getOtpNum().equals(otp)){
            return "Invalid otp";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password reset successful";
    }

    public UserInfoDto getUserInfoByDocId(Long docId){
        Optional<UserInfoDto> userOtp = userRepository.findUserInfoByDocumentId(docId);
        if(userOtp.isEmpty()) {
            return null;
        }
        UserInfoDto userInfoDto = userOtp.get();
        return userInfoDto;
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
