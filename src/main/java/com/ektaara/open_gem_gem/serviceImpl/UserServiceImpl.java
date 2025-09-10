package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.dto.OtpVerificationResponse;
import com.ektaara.open_gem_gem.dto.UserResponse;
import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.repository.UserRepository;
import com.ektaara.open_gem_gem.service.OtpTemplateService;
import com.ektaara.open_gem_gem.service.SmsSender;
import com.ektaara.open_gem_gem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SmsSender smsSender;
    private final OtpTemplateService otpTemplateService;

    @Override
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

    @Override
    public OtpVerificationResponse verifyOtpAndRegister(String phone, String otp, String name) {
        return userRepository.findByPhone(phone)
                .filter(user -> user.getOtp().equals(otp) && user.getOtpExpiry().isAfter(LocalDateTime.now()))
                .map(user -> {
                    user.setPhoneVerified(true);
                    user.setName(name);
                    user.setOtp(null);
                    user.setOtpExpiry(null);
                    userRepository.save(user);
                    log.info("User Response for verification");
                    return OtpVerificationResponse.builder()
                            .isVerified(true)
                            .user(UserResponse.builder()
                                    .email(user.getEmail())
                                    .name(user.getName())
                                    .phone(user.getPhone())
                                    .otp(user.getOtp())
                                    .isPhoneVerified(user.isPhoneVerified())
                                    .build())
                            .build();
                })
                .orElse(OtpVerificationResponse.builder().isVerified(false).build());
    }

    @Override
    public void setNotificationsEnabled(String phone, boolean enabled) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setNotificationsEnabled(enabled);
        userRepository.save(user);
    }

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}
