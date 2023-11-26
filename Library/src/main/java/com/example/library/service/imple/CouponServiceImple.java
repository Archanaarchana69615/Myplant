package com.example.library.service.imple;

import com.example.library.dto.CouponDto;
import com.example.library.model.Coupon;
import com.example.library.repository.CouponRepository;
import com.example.library.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImple implements CouponService {
    private CouponRepository couponRepository;

    public CouponServiceImple(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        List<Coupon>couponList=couponRepository.findAllCoupons();

        return couponList;
    }

    @Override
    public Coupon save(CouponDto couponDto) {
        Coupon coupon=new Coupon();
        coupon.setId(couponDto.getId());
        coupon.setCode(couponDto.getCode());
        coupon.setDescription(couponDto.getCode());
        coupon.setCount(couponDto.getCount());
        coupon.setMinOrderAmount(couponDto.getMinOrderAmount());
        coupon.setOffPercentage(couponDto.getOffPercentage());
        coupon.setMaxOff(couponDto.getMaxOff());
        coupon.setExpiryDate(couponDto.getExpiryDate());
        coupon.setEnabled(true);
        couponRepository.save(coupon);

        return coupon;
    }

    @Override
    public CouponDto findById(long id) {
    Coupon coupon=couponRepository.findById(id);
    CouponDto couponDto=new CouponDto();
    couponDto.setId(coupon.getId());
    couponDto.setDescription(coupon.getDescription());
    couponDto.setCode(coupon.getCode());
    couponDto.setCount(coupon.getCount());
    couponDto.setExpiryDate(coupon.getExpiryDate());
    couponDto.setEnabled(coupon.isEnabled());
    couponDto.setOffPercentage(coupon.getOffPercentage());
    couponDto.setMaxOff(coupon.getMaxOff());
        return couponDto;
    }

    @Override
    public Coupon update(CouponDto couponDto) {
        long id=couponDto.getId();
        Coupon coupon=couponRepository.findById(id);
        coupon.setId(couponDto.getId());
        coupon.setCode(couponDto.getCode());
        coupon.setDescription(couponDto.getDescription());
        coupon.setCount(couponDto.getCount());
        coupon.setMaxOff(couponDto.getMaxOff());
        coupon.setExpiryDate(couponDto.getExpiryDate());
        coupon.setMinOrderAmount(couponDto.getMinOrderAmount());
        couponRepository.save(coupon);

        return coupon;
    }

    @Override
    public void disable(long id) {
        Coupon coupon=couponRepository.findById(id);
        coupon.setEnabled(false);
        couponRepository.save(coupon);
    }

    @Override
    public void enable(long id) {
        Coupon coupon=couponRepository.findById(id);
        coupon.setEnabled(true);
        couponRepository.save(coupon);
    }

    @Override
    public void delete(long id) {
        Coupon coupon=couponRepository.findById(id);
        couponRepository.save(coupon);
    }

    @Override
    public boolean findByCouponCode(String couponCode) {
        Coupon coupon=couponRepository.findCouponByCode(couponCode);
        if(coupon==null)
        {
            return false;
        } else if (!coupon.isEnabled()||coupon.isExpired())
        {
            return false;
        }
        return true;
    }

    @Override
    public Coupon findByCode(String couponCode) {
        return couponRepository.findCouponByCode(couponCode);
    }

    @Override
    public Double applyCoupon(String couponCode, double totalPrice) {
        Coupon coupon=couponRepository.findCouponByCode(couponCode);
        double discountPrice= totalPrice *(coupon.getOffPercentage()/100.0);

        if(discountPrice>coupon.getMaxOff()) {
            discountPrice = coupon.getMaxOff();
        }
        coupon.setCount(coupon.getCount()-1);
        couponRepository.save(coupon);
        double updatedTotalPrice=totalPrice-discountPrice;
        String formattedTotalPrice=String.format("%2f",updatedTotalPrice);
        return Double.parseDouble(formattedTotalPrice);
    }



    public List<CouponDto> transferData(List<Coupon>couponList)
{
    List<CouponDto>couponDtoList=new ArrayList<>();
    for(Coupon coupon:couponList) {
        CouponDto couponDto = new CouponDto();
        couponDto.setId(coupon.getId());
        couponDto.setCount(coupon.getCount());
        couponDto.setDescription(coupon.getDescription());
        couponDto.setExpiryDate(coupon.getExpiryDate());
        couponDto.setOffPercentage(coupon.getOffPercentage());
        couponDto.setMaxOff(coupon.getMaxOff());
        couponDto.setEnabled(coupon.isEnabled());
        couponDto.setCode(coupon.getCode());
        couponDto.setMinOrderAmount(coupon.getMinOrderAmount());
    }
   return couponDtoList;
    }



}
