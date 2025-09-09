package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SmsSender smsSender;
    private final OtpTemplateService otpTemplateService;

    public void sendOtp(String phone) {
        String otp = generateOtp();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        User user = userRepository.findByPhone(phone)
                .map(existing -> {
                    existing.setOtp(otp);
                    existing.setOtpExpiry(expiry);
                    return existing;
                })
                .orElse(User.builder()
                        .phone(phone)
                        .otp(otp)
                        .otpExpiry(expiry)
                        .isPhoneVerified(false)
                        .build());

        userRepository.save(user);
        String message = otpTemplateService.buildOtpMessage(otp);
        smsSender.sendSms(phone, message);
    }

    public boolean verifyOtpAndRegister(String phone, String otp, String name) {
        Optional<User> optionalUser = userRepository.findByPhone(phone);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getOtp().equals(otp) && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
                user.setPhoneVerified(true);
                user.setName(name);
                user.setOtp(null);
                user.setOtpExpiry(null);
                userRepository.save(user);
                return true;
            }
        }

        return false;
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void setNotificationsEnabled(String phone, boolean enabled) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNotificationsEnabled(enabled);
        userRepository.save(user);
    }
}
