package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByPhone(String phone);
    void deleteByPhone(String phone);
}
