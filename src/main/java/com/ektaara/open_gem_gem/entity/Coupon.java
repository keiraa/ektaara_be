package com.ektaara.open_gem_gem.entity;

import com.ektaara.open_gem_gem.enums.CouponStatus;
import com.ektaara.open_gem_gem.enums.CouponType;
import com.ektaara.open_gem_gem.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "coupon")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String couponCode;

    private Double minValue;

    private Double maxDiscount;

    private Double discount;

    private String couponDescription;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "coupon_category",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    @Enumerated(EnumType.STRING)
    private CouponType couponType;
}
