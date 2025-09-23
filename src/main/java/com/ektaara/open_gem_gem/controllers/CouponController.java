package com.ektaara.open_gem_gem.controllers;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.dto.CouponDTO;
import com.ektaara.open_gem_gem.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponDTO> addCoupon(@RequestBody CouponDTO couponDTO) {
        return ResponseEntity.ok(couponService.addCoupon(couponDTO));
    }

    @GetMapping
    public ResponseEntity<List<CouponDTO>> getCoupons() {
        return ResponseEntity.ok(couponService.getCoupons());
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateCoupon(@RequestParam String couponCode, @RequestBody CartDTO cart) {
        return ResponseEntity.ok(couponService.validateCoupon(couponCode, cart));
    }

    @PostMapping("/validate-voucher")
    public ResponseEntity<Boolean> validateVoucher(@RequestParam String couponCode) {
        return ResponseEntity.ok(couponService.validateVoucher(couponCode));
    }

    @PutMapping("/{couponCode}/activate")
    public ResponseEntity<CouponDTO> activateCoupon(@PathVariable String couponCode) {
        return ResponseEntity.ok(couponService.activateDeactivateCoupon(couponCode, true));
    }

    @PutMapping("/{couponCode}/deactivate")
    public ResponseEntity<CouponDTO> deactivateCoupon(@PathVariable String couponCode) {
        return ResponseEntity.ok(couponService.activateDeactivateCoupon(couponCode, false));
    }
}
