package com.ektaara.open_gem_gem.service;

public interface SmsSender {
    void sendSms(String phoneNumber, String message);
}
