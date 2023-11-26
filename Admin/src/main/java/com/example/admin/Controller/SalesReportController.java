package com.example.admin.Controller;

import com.example.library.dto.ProductDto;
import com.example.library.model.Order;
import com.example.library.model.Product;
import com.example.library.service.OrderService;
import com.example.library.service.Productservice;
import com.example.library.service.SalesReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class SalesReportController {
    private OrderService orderService;

    private Productservice productservice;

    private SalesReportService salesReportService;

  @GetMapping("/salesReport")
    public String viewReport(Model model)
  {
      List<Order>orders=orderService.findAllOrders();
//      System.out.println(orders);
      model.addAttribute("orders",orders);
      model.addAttribute("tittle",orders.size());

      List<ProductDto>productDtoList=productservice.findAllProducts();
      model.addAttribute("size",productDtoList);

      List<Product>allProducts=salesReportService.findAllByProduct();
      List<Object[]>productEarnigs=salesReportService.findAllRevenue();
      System.out.println(productEarnigs);

      model.addAttribute("productEarnings",productEarnigs);
      model.addAttribute("size",productEarnigs.size());
      model.addAttribute("title","sales report");

      long totalQuantity=0;
      double totalRevenue=0.0;

      for (Object[]productEarnings :productEarnigs)
      {
        long quantitySold = ((Number)productEarnings[4]).longValue();
        double costPrice = ((Number)productEarnings[3]).doubleValue();
        double revenue = quantitySold*costPrice;

        totalQuantity+= quantitySold;
        totalRevenue+= revenue;

      }
      model.addAttribute("totalQuantity",totalQuantity);
      model.addAttribute("totalRevenue",totalRevenue);

      return "salesReport";
  }
  @GetMapping("/salesReport/filter")
    public String filterProducts(@RequestParam(name="month")int selectedmonth,
                                    @RequestParam(name="year")int selectedyear,Model model)
  {
   List<Object[]>filteredProducts=salesReportService.findProductSoldAndEarningsFilter(selectedmonth,selectedyear);
   model.addAttribute("filteredProducts",filteredProducts);
   model.addAttribute("size",filteredProducts.size());
   model.addAttribute("title","salesReport");

   int totalQuantity=0;
   double totalRevenue= 0.0;
   for(Object[]productEarning :filteredProducts)
   {
  long quantitySold=((Number)productEarning[4]).longValue();
  double costPrice=((Number)productEarning[3]).doubleValue();
  double revenue = quantitySold*costPrice;

  totalQuantity+= quantitySold;
  totalRevenue+= revenue;
   }
   model.addAttribute("totalQuantity",totalQuantity);
   model.addAttribute("totalRevenue",totalRevenue);

   return "salesReportFilter";
  }

}
