package com.example.library.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {

    private long id;

    private String code;

    private int count;

    private int offPercentage;

    private String description;

    private int maxOff;


    private int minOrderAmount;

    private LocalDate expiryDate;

    private boolean enabled;


}
