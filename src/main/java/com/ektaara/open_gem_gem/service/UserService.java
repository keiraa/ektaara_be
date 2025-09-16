package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.dto.OtpVerificationResponse;
import com.ektaara.open_gem_gem.dto.UserDetailsRequest;
import com.ektaara.open_gem_gem.entity.User;

import java.util.Optional;

public interface UserService {

    void sendOtp(String phone);

    User findByPhoneOrThrow(String phone);

    OtpVerificationResponse verifyOtpAndRegister(String phone, String otp);

    void setNotificationsEnabled(String phone, boolean enabled);

    void updateMissingUserDetails(String phone, UserDetailsRequest request);

}
