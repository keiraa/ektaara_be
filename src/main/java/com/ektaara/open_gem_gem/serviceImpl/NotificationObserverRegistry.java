package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.entity.User;
import com.ektaara.open_gem_gem.service.Notifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationObserverRegistry {

    private final List<Notifier> notifiers;

    public NotificationObserverRegistry(List<Notifier> notifiers) {
        this.notifiers = notifiers;
    }

    public void notifyAllObservers(User user, String message) {
        for (Notifier notifier : notifiers) {
            notifier.notify(user, message);
        }
    }
}
