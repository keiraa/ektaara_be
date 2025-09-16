package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.dto.OtpVerificationResponse;
import com.ektaara.open_gem_gem.dto.UserDetailsRequest;
import com.ektaara.open_gem_gem.dto.UserResponse;
import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.model.Role;
import com.ektaara.open_gem_gem.repository.UserRepository;
import com.ektaara.open_gem_gem.security.JwtService;
import com.ektaara.open_gem_gem.security.RefreshTokenService;
import com.ektaara.open_gem_gem.service.OtpTemplateService;
import com.ektaara.open_gem_gem.service.SmsSender;
import com.ektaara.open_gem_gem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SmsSender smsSender;
    private final OtpTemplateService otpTemplateService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

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

    public User findByPhoneOrThrow(String phone) {
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public OtpVerificationResponse verifyOtpAndRegister(String phone, String otp) {
        return userRepository.findByPhone(phone)
                .filter(user -> user.getOtp().equals(otp) && user.getOtpExpiry().isAfter(LocalDateTime.now()))
                .map(user -> {
                    user.setPhoneVerified(true);
                    user.setOtp(null);
                    user.setOtpExpiry(null);
                    user.setRole(Role.USER);
                    userRepository.save(user);

                    String accessToken = jwtService.generateToken(user, 1000 * 60 * 15); // 15 min
                    String refreshToken = refreshTokenService.createOrUpdateToken(phone).getToken();

                    return OtpVerificationResponse.builder()
                            .isVerified(true)
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .user(UserResponse.builder()
                                    .email(user.getEmail())
                                    .name(user.getName())
                                    .phone(user.getPhone())
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

    public void updateMissingUserDetails(String phone, UserDetailsRequest request) {
        User user = findByPhoneOrThrow(phone);

        boolean updated = false;

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(request.getName());
            updated = true;
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
            updated = true;
        }

        if (updated) {
            userRepository.save(user);
        }
    }
}
