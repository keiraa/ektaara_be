package com.ektaara.open_gem_gem.dto;

import lombok.Data;

@Data
public class OtpVerificationRequest {
    private String phone;
    private String otp;
    private String name;
}
