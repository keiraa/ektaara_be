package com.ektaara.open_gem_gem.service;

import com.ektaara.open_gem_gem.entity.User;

public interface Notifier {
    void notify(User user, String message);
}
