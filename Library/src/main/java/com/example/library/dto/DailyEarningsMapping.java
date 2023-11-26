package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DailyEarningsMapping {
    private String date;

    private double earnings;

    public DailyEarningsMapping(String date,double earnings)
    {
        this.date=date;
        this.earnings=earnings;

    }
}
