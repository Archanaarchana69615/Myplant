package com.example.library.repository;

import com.example.library.dto.CouponDto;
import com.example.library.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository< Coupon,Long> {

    Coupon findById(long id);

    Coupon findCouponByCode(String code);
    @Query("select c from Coupon c")
    List<Coupon> findAllCoupons();


}
