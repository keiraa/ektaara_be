package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.*;
import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.exceptions.InvalidTokenException;
import com.ektaara.open_gem_gem.security.JwtService;
import com.ektaara.open_gem_gem.security.RefreshTokenService;
import com.ektaara.open_gem_gem.security.TokenPair;
import com.ektaara.open_gem_gem.service.UserService;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;


    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody PhoneRequest request) {
        userService.sendOtp(request.getPhone());
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(@RequestBody OtpVerificationRequest request) {
        OtpVerificationResponse verifiedResponse = userService.verifyOtpAndRegister(
                request.getPhone(),
                request.getOtp()
        );

        return ResponseEntity.ok(verifiedResponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenPair> refreshToken(@RequestBody RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!refreshTokenService.validateToken(refreshToken)) {
            throw new InvalidTokenException("Invalid or expired refresh token.");
        }

        String phone = refreshTokenService.getPhoneFromToken(refreshToken);
        User user = userService.findByPhoneOrThrow(phone);

        String newAccessToken = jwtService.generateToken(user, 15 * 60 * 1000); // 15 min
        return ResponseEntity.ok(new TokenPair(newAccessToken, refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String phone) {
        refreshTokenService.deleteByPhone(phone);
        return ResponseEntity.ok("Logged out successfully.");
    }

    @PostMapping("/details")
    public ResponseEntity<?> updateUserDetails(@RequestBody UserDetailsRequest request) {
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateMissingUserDetails(phone, request);
        return ResponseEntity.ok("User details updated successfully.");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        String phone = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByPhoneOrThrow(phone);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .isPhoneVerified(user.isPhoneVerified())
                .isNotificationEnabled(user.isNotificationsEnabled())
                .build();

        return ResponseEntity.ok(response);
    }


//    @PutMapping("/change-role")
//    public ResponseEntity<?> changeUserRole(@RequestParam String phone, @RequestParam Role newRole) {
//        userService.updateUserRole(phone, newRole);
//        return ResponseEntity.ok("User role updated.");
//    }

    @PutMapping("/notifications/opt-in")
    public ResponseEntity<?> enableNotifications(@RequestParam String phone) {
        userService.setNotificationsEnabled(phone, true);
        return ResponseEntity.ok("Notifications enabled");
    }

    @PutMapping("/notifications/opt-out")
    public ResponseEntity<?> disableNotifications(@RequestParam String phone) {
        userService.setNotificationsEnabled(phone, false);
        return ResponseEntity.ok("Notifications disabled");
    }
}
