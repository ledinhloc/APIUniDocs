package com.android.APILogin.service;

import com.android.APILogin.dto.request.OtpRequest;
import com.android.APILogin.entity.User;
import com.android.APILogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
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
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        if(! passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid password";
        }

        if(! user.isActive()){
            return "User is not active";
        }

        return "Login successful";
    }

    public User createUser(User user) {
        User newUser = User.builder()
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .age(user.getAge())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .isActive(false)
                .otpExpiry(LocalDateTime.now().plusMinutes(5))
                .otp(generateOtp())
                .build();

        sendOtp(newUser.getEmail(), newUser.getOtp());
        return userRepository.save(newUser);
    }

    public String verifyOtpForActivation(OtpRequest otpRequest) {
        Optional<User> userOpt = userRepository.findByEmail(otpRequest.getEmail());
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        if(user.getOtpExpiry().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(!user.getOtp().equals(otpRequest.getOtp())) {
            return "Invalid otp";
        }

        user.setActive(true);
        userRepository.save(user);
        return "User activated successfully!";
    }

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
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
        user.setOtp(generateOtp());
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        sendOtp(user.getEmail(), user.getOtp());
        userRepository.save(user);
        return "Otp sent for reset password";
    }

    public String verifyOtpForPasswordReset(String email, String otp, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();
        if(user.getOtpExpiry().isBefore(LocalDateTime.now())){
            return "OTP expired";
        }

        if(! user.getOtp().equals(otp)){
            return "Invalid otp";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "Password reset successful";
    }
}
