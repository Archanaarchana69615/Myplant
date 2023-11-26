package com.example.library.dto;

import lombok.Data;

@Data
public class TotalPriceByPayment {
    private String payMethod;

    private double amount;


public TotalPriceByPayment(String payMethod,double amount)
{
    this.amount=amount;
    this.payMethod=payMethod;
}

}
