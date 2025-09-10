package com.ektaara.open_gem_gem.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OtpVerificationResponse {
    private boolean isVerified;
    private UserResponse user;
}
