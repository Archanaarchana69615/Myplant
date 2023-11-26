package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coupons")
public class Coupon {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "coupon_id")
    private Long id;

   private String code;

   private String description;

   private int count;

   private int offPercentage;

   private int maxOff;

   private LocalDate expiryDate;

   private boolean enabled;

   private int minOrderAmount;

   public boolean isExpired() {
       return (this.count == 0 || this.expiryDate.isBefore(LocalDate.now()));
   }

}
