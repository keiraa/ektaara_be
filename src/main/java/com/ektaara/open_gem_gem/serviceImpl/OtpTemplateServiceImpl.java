package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.service.OtpTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class OtpTemplateServiceImpl implements OtpTemplateService {

    private final TemplateEngine templateEngine;

    @Override
    public String buildOtpMessage(String otp) {
        Context context = new Context();
        context.setVariable("otp", otp);
        return templateEngine.process("otp_sms.txt", context);
    }
}
