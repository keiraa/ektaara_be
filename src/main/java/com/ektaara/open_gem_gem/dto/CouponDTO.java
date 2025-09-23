package com.ektaara.open_gem_gem.dto;

import com.ektaara.open_gem_gem.enums.CouponStatus;
import com.ektaara.open_gem_gem.enums.CouponType;
import com.ektaara.open_gem_gem.enums.DiscountType;
import lombok.Data;

import java.util.Set;

@Data
public class CouponDTO {
    private Long id;
    private String couponCode;
    private double minValue;
    private double maxDiscount;
    private double discount;
    private DiscountType discountType;
    private Set<Long> categoryIds;
    private CouponStatus status;
    private CouponType couponType;
}
