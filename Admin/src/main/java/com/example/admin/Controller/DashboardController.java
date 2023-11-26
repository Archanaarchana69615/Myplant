package com.example.admin.Controller;

import com.example.library.dto.DailyEarnings;
import com.example.library.dto.DailyEarningsMapping;
import com.example.library.dto.TotalPriceByPayment;
import com.example.library.service.Dashboardservice;
import com.example.library.service.Productservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import com.example.library.service.Categoryservice;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DashboardController {
    private final Dashboardservice dashboardservice;

    public DashboardController(Dashboardservice dashboardservice) {
        this.dashboardservice = dashboardservice;
    }

    @RequestMapping(value = {"/index", "/"})
    public String home(Model model, Principal principal) throws JsonProcessingException {
        if (principal == null) {
            return "login";
        }
        model.addAttribute("title", "home page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        YearMonth currentYear = YearMonth.now();
        LocalDate localStartDate = LocalDate.of(currentYear.getYear(), currentYear.getMonthValue(), 1);
        LocalDate localEndDate = currentYear.atEndOfMonth();
        Date startDate = java.sql.Date.valueOf(localStartDate);
        Date endDate = java.sql.Date.valueOf(localEndDate);
        double currentMonthEarning = dashboardservice.findCurrentMonthOrder(startDate, endDate);
        System.out.println(currentMonthEarning);
        Month currentMonth = currentYear.getMonth();
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("currentMonthEarning", currentMonthEarning);


        LocalDate localStartDateYearly = LocalDate.of(currentYear.getYear(), Month.JANUARY, 1);
        LocalDate localEndDateYearly = LocalDate.of(currentYear.getYear(), Month.DECEMBER, 31);
        System.out.println(localEndDateYearly);
        Date startDateYearly = java.sql.Date.valueOf(localStartDateYearly);
        Date endDateYearly = java.sql.Date.valueOf(localEndDateYearly);
        double currentYearlyEarning = dashboardservice.findCurrentMonthOrder(startDateYearly, endDateYearly);
        int year = currentYear.getYear();
        model.addAttribute("currentYear", year);
        model.addAttribute("currentYearlyEarning", currentYearlyEarning);

        int totalOrders = (int) dashboardservice.findOrdersTotal();
        model.addAttribute("totalOrders", totalOrders);
        int totalOrderPending = (int) dashboardservice.findOrdersPending();
        model.addAttribute("totalOrderPending", totalOrderPending);
        int progress = 0;
        if (totalOrders != 0) {
            progress = (totalOrderPending) * 100 / totalOrders;
        } else {
            progress = 0;
        }
        model.addAttribute("progress", progress);

        int currentYr = currentYear.getYear();
        int currentMnth=currentYear.getMonthValue();
        List<Object[]>dailyEarning=dashboardservice.retrieveDailyEarnings(currentYr,currentMnth);
        List<DailyEarningsMapping>dailyEarningListForJson=new ArrayList<>();
        for (Object[] obj:dailyEarning)
        {
          Date date=(Date) obj[0];

          Double amount= (Double) obj[1];
            DailyEarnings dailyEarnings1=new DailyEarnings(date,amount);
            String input=dailyEarnings1.toString();
            String datePart=input.substring(input.indexOf("date=")+"date".length(),input.indexOf(" "));
            DailyEarningsMapping dailyEarningsMapping=new DailyEarningsMapping(datePart,amount);
            dailyEarningListForJson.add(dailyEarningsMapping);
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String dailyEarningJson=objectMapper.writeValueAsString(dailyEarningListForJson);
        model.addAttribute("dailyEarnings",dailyEarningJson);

        List<Object[]>priceByPayMethod=dashboardservice.findTotalPricesByPayment();
        List<TotalPriceByPayment>totalPriceByPaymentList=new ArrayList<>();
        for(Object[] obj:priceByPayMethod)
        {
          String payMethod=(String) obj[0];
          Double amount=(Double) obj[1];
          TotalPriceByPayment totalPriceByPayment=new TotalPriceByPayment(payMethod,amount);
          totalPriceByPaymentList.add(totalPriceByPayment);
        }
        ObjectMapper objectMapper1=new ObjectMapper();
        String totalPriceByPayment= objectMapper1.writeValueAsString(totalPriceByPaymentList);
        model.addAttribute("totalPriceByPayment",totalPriceByPayment);
        System.out.println(totalPriceByPayment);
        return "Index";
    }
}