package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private long Id;


    private String addressline1;

    private String addressline2;

    private String pincode;


    private String state;

    private String district;

    private String city;

    private String country;


}
