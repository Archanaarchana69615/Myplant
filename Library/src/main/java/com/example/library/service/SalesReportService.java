package com.example.library.service;

import com.example.library.model.Product;

import java.util.List;

public interface SalesReportService {

    List<Product> findAllByProduct();

    List<Object[]>findAllRevenue();

    List<Object[]>findProductSoldAndEarningsFilter(int month,int year);
}
