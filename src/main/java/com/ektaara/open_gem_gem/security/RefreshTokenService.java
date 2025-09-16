package com.ektaara.open_gem_gem.security;

import com.ektaara.open_gem_gem.entity.RefreshToken;
import com.ektaara.open_gem_gem.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createOrUpdateToken(String phone) {
        String token = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusSeconds(60 * 60 * 24 * 30); // 30 days

        return refreshTokenRepository.findByPhone(phone)
                .map(existing -> {
                    existing.setToken(token);
                    existing.setExpiryDate(expiry);
                    return refreshTokenRepository.save(existing);
                })
                .orElseGet(() -> refreshTokenRepository.save(
                        RefreshToken.builder()
                                .phone(phone)
                                .token(token)
                                .expiryDate(expiry)
                                .build()));
    }

    public boolean validateToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }

    public String getPhoneFromToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getPhone)
                .orElse(null);
    }

    public void deleteByPhone(String phone) {
        refreshTokenRepository.deleteByPhone(phone);
    }
}
