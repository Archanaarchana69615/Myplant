package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DailyEarnings {
    private Date date;

    private double earnings;

public DailyEarnings(Date Date,Double earnings)
{
    this.date=date;
    this.earnings=earnings;
}

}