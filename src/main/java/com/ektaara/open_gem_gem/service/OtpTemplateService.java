package com.ektaara.open_gem_gem.service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpTemplateService {

    private final TemplateEngine templateEngine;

    public String buildOtpMessage(String otp) {
        Context context = new Context();
        context.setVariable("otp", otp);
        return templateEngine.process("otp_sms.txt", context);
    }
}

