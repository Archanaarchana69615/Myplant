package com.example.library.service;

import com.example.library.dto.CouponDto;
import com.example.library.model.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponService {

    List<Coupon>getAllCoupons();

    Coupon save(CouponDto couponDto);

    CouponDto findById(long id);

    Coupon update(CouponDto couponDto);

    void disable(long id);

    void enable(long id);

    void delete(long id);

    boolean findByCouponCode(String couponCode);

    Coupon findByCode(String couponCode);

    Double applyCoupon(String couponCode,double totalPrice);


}
