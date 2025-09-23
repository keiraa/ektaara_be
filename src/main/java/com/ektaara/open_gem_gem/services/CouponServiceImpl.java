package com.ektaara.open_gem_gem.services;

import com.ektaara.open_gem_gem.dto.CartDTO;
import com.ektaara.open_gem_gem.dto.CouponDTO;
import com.ektaara.open_gem_gem.entity.Category;
import com.ektaara.open_gem_gem.entity.Coupon;
import com.ektaara.open_gem_gem.enums.CouponStatus;
import com.ektaara.open_gem_gem.enums.CouponType;
import com.ektaara.open_gem_gem.repository.CategoryRepository;
import com.ektaara.open_gem_gem.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {


    private CouponRepository couponRepository;

    private CategoryRepository categoryRepository;

    @Override
    public CouponDTO addCoupon(CouponDTO couponDTO) {
        Coupon coupon = toEntity(couponDTO);
        return toDTO(couponRepository.save(coupon));
    }

    @Override
    public List<CouponDTO> getCoupons() {
        return couponRepository.findAllByCouponType(CouponType.COUPON).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateCoupon(String couponCode, CartDTO cart) {
        return couponRepository.findByCouponCode(couponCode)
                .map(coupon -> {
                    if (coupon.getStatus() == CouponStatus.ACTIVE && coupon.getCouponType() == CouponType.COUPON) {
                        // Further validation logic based on cart and coupon properties can be added here
                        return true;
                    }
                    return false;
                }).orElse(false);
    }

    @Override
    public boolean validateVoucher(String couponCode) {
        return couponRepository.findByCouponCode(couponCode)
                .map(coupon -> coupon.getStatus() == CouponStatus.ACTIVE && coupon.getCouponType() == CouponType.VOUCHER)
                .orElse(false);
    }

    @Override
    public CouponDTO activateDeactivateCoupon(String couponCode, boolean activate) {
        return couponRepository.findByCouponCode(couponCode)
                .map(coupon -> {
                    coupon.setStatus(activate ? CouponStatus.ACTIVE : CouponStatus.INACTIVE);
                    return toDTO(couponRepository.save(coupon));
                }).orElse(null); // Or throw an exception
    }

    private CouponDTO toDTO(Coupon coupon) {
        CouponDTO dto = new CouponDTO();
        dto.setId(coupon.getId());
        dto.setCouponCode(coupon.getCouponCode());
        dto.setMinValue(coupon.getMinValue());
        dto.setMaxDiscount(coupon.getMaxDiscount());
        dto.setDiscount(coupon.getDiscount());
        dto.setDiscountType(coupon.getDiscountType());
        dto.setCategoryIds(coupon.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
        dto.setStatus(coupon.getStatus());
        dto.setCouponType(coupon.getCouponType());
        return dto;
    }

    private Coupon toEntity(CouponDTO dto) {
        Coupon coupon = new Coupon();
        coupon.setId(dto.getId());
        coupon.setCouponCode(dto.getCouponCode());
        coupon.setMinValue(dto.getMinValue());
        coupon.setMaxDiscount(dto.getMaxDiscount());
        coupon.setDiscount(dto.getDiscount());
        coupon.setDiscountType(dto.getDiscountType());
        coupon.setCategories(dto.getCategoryIds().stream()
                .map(id -> categoryRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id)))
                .collect(Collectors.toList()));
        coupon.setStatus(dto.getStatus());
        coupon.setCouponType(dto.getCouponType());
        return coupon;
    }
}
