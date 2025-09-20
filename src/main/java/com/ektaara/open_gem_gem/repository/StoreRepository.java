package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    Optional<Store> findByStoreName(String storeName);
}