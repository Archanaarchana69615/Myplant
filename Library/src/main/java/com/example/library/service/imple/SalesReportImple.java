package com.example.library.service.imple;

import com.example.library.model.Product;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.repository.ProductRepository;
import com.example.library.service.SalesReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Service
public class SalesReportImple implements SalesReportService {

    private OrderDetailRepository orderDetailRepository;

    private ProductRepository productRepository;
    @Override
    public List<Product> findAllByProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Object[]> findAllRevenue() {
        System.out.println("rep"+orderDetailRepository.findProductsSoldAndRevenue());
        return orderDetailRepository.findProductsSoldAndRevenue();
    }

    @Override
    public List<Object[]> findProductSoldAndEarningsFilter(int month, int year) {
        return orderDetailRepository.findProductSoldAndRevenueByMonthAndYear(month, year);
    }


}
