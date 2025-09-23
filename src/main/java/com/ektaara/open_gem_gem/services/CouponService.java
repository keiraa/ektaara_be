package com.ektaara.open_gem_gem.services;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.dto.CouponDTO;

import java.util.List;

public interface CouponService {
    CouponDTO addCoupon(CouponDTO couponDTO);
    List<CouponDTO> getCoupons();
    boolean validateCoupon(String couponCode, CartDTO cart);
    boolean validateVoucher(String couponCode);
    CouponDTO activateDeactivateCoupon(String couponCode, boolean activate);
}
