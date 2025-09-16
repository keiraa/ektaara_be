package com.ektaara.open_gem_gem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String phone;

    private String name;

    private String email;

    private boolean isPhoneVerified;

    private String otp;
}
