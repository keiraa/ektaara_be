package com.ektaara.open_gem_gem.repository;

import com.ektaara.open_gem_gem.entity.Coupon;
import com.ektaara.open_gem_gem.enums.CouponType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCouponCode(String couponCode);
    List<Coupon> findAllByCouponType(CouponType couponType);
}
