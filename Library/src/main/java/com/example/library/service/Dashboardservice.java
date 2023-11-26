package com.example.library.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface Dashboardservice {
    double findCurrentMonthOrder(Date startDate, Date endDate);

    long findOrdersTotal();

    int findOrdersPending();

    List<Object[]>retrieveDailyEarnings(int currentYr,int currentMnt);

    List<Object[]>findTotalPricesByPayment();
}
