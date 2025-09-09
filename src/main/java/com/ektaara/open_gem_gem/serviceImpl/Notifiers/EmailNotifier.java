package com.ektaara.open_gem_gem.serviceImpl.Notifiers;

import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.service.Notifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailNotifier implements Notifier {

//    private final EmailService emailService;

    @Override
    public void notify(User user, String message) {
        if (user.isNotificationsEnabled() && user.getEmail() != null) {
//            emailService.sendEmail(user.getEmail(), "Notification", message);
        }
    }
}
