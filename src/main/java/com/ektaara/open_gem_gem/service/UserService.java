package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.dto.OtpVerificationResponse;

public interface UserService {

    void sendOtp(String phone);

    OtpVerificationResponse verifyOtpAndRegister(String phone, String otp, String name);

    void setNotificationsEnabled(String phone, boolean enabled);

}
