package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/broadcast")
    public ResponseEntity<?> broadcast(@RequestBody String message) {
        notificationService.broadcast(message);
        return ResponseEntity.ok("Notification sent to all opted-in users.");
    }
}
