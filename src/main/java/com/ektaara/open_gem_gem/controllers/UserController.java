package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.OtpVerificationRequest;
import com.ektaara.open_gem_gem.dto.PhoneRequest;
import com.ektaara.open_gem_gem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody PhoneRequest request) {
        userService.sendOtp(request.getPhone());
        return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        boolean verified = userService.verifyOtpAndRegister(
                request.getPhone(),
                request.getOtp(),
                request.getName()
        );

        return verified
                ? ResponseEntity.ok("User verified and registered.")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired OTP.");
    }

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
