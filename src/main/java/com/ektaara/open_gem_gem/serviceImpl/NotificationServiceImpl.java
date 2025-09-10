package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.repository.UserRepository;
import com.ektaara.open_gem_gem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationObserverRegistry observerRegistry;

    @Override
    public void broadcast(String message) {
        List<User> recipients = userRepository.findAllByNotificationsEnabledTrue();

        for (User user : recipients) {
            observerRegistry.notifyAllObservers(user, message);
        }
    }
}
