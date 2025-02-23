package com.android.APILogin.service;

import com.android.APILogin.dto.request.OtpRequest;
import com.android.APILogin.entity.Account;
import com.android.APILogin.entity.OTP;
import com.android.APILogin.entity.User;
import com.android.APILogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public String loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByAccount_Email(email);

        if(userOpt.isEmpty()) {
            return "User is not found";
        }

        User user = userOpt.get();
        if(! passwordEncoder.matches(password, user.getAccount().getPassword())) {
            return "Invalid password";
        }

        if(! user.getAccount().getIs_active()){
            return "User is not active";
        }

        return "Login successful";
    }

    public String createUser(User user) {

        Account account = Account.builder()
                .email(user.getAccount().getEmail())
                .password(passwordEncoder.encode(user.getAccount().getPassword()))
                .created_at(LocalDateTime.now())
                .update_at(LocalDateTime.now())
                .type("NORMAL")
                .is_active(false)
                .build();

        // Tạo OTP cho User
        OTP otp = OTP.builder()
                .otp_num(generateOtp())
                .otp_expired(LocalDateTime.now().plusMinutes(5)) // OTP hết hạn sau 5 phút
                .user(user)
                .build();

        User newUser = User.builder()
                .name(user.getName())
                .role(user.getRole())
                .dob(user.getDob())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .gender(user.getGender())
                .address(user.getAddress())
                .last_online(LocalDateTime.now())
                .status(null)
                .account(account)
                .otps(List.of(otp))
                .build();

        sendOtp(newUser.getAccount().getEmail(), otp.getOtp_num());
        userRepository.save(newUser);
        return "Create user successful";
    }

    public String verifyOtpForActivation(OtpRequest otpRequest) {
        Optional<User> userOpt = userRepository.findByAccount_Email(otpRequest.getEmail());
        if(userOpt.isEmpty()) {
            return "User is not found";
        }

        User user = userOpt.get();
        OTP otp = user.getOtps().get(0);

        if(otp.getOtp_expired().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(!otp.getOtp_num().equals(otpRequest.getOtp())) {
            return "Invalid otp";
        }

        user.getAccount().setIs_active(true);
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
        Optional<User> userOpt = userRepository.findByAccount_Email(email);
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        //tạo otp
        User user = userOpt.get();
        OTP otp = OTP.builder()
                .otp_num(generateOtp())
                .otp_expired(LocalDateTime.now().plusMinutes(5))
                .user(user)
                .build();
        user.setOtps(List.of(otp));

        sendOtp(user.getAccount().getEmail(), otp.getOtp_num());
        userRepository.save(user);
        return "Otp sent for reset password";
    }

    public String verifyOtpForPasswordReset(String email, String otp, String newPassword) {
        Optional<User> userOpt = userRepository.findByAccount_Email(email);
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        OTP otpEntity = user.getOtps().get(0);

        if(otpEntity.getOtp_expired().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(! otpEntity.getOtp_num().equals(otp)){
            return "Invalid otp";
        }

        user.getAccount().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password reset successful";
    }
}
