package com.example.customer.controller;

//import com.example.library.dto.WalletHistoryDto;
import com.example.library.dto.WalletHistoryDto;
import com.example.library.model.Customer;
import com.example.library.model.Wallet;
import com.example.library.model.WalletHistory;
import com.example.library.repository.WalletHistoryRepository;
import com.example.library.service.CustomerService;
import com.example.library.service.WalletService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class WalletController {

    private WalletService walletService;

    private WalletHistoryRepository walletHistoryRepository;


    private CustomerService customerService;

    public WalletController(WalletService walletService, WalletHistoryRepository walletHistoryRepository, CustomerService customerService) {
        this.walletService = walletService;
        this.walletHistoryRepository = walletHistoryRepository;
        this.customerService = customerService;
    }

    @GetMapping("/wallets")
    public String wallet(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByEmail(principal.getName());
        Wallet wallet = walletService.findByCustomer(customer);
        List<WalletHistoryDto> walletHistoryDtoList = walletService.findAllById(wallet.getId());

        model.addAttribute("walletHistoryList",walletHistoryDtoList);
        model.addAttribute("wallet",wallet);
        return "wallets";
    }
    @RequestMapping(value = "/add-wallet",method = RequestMethod.POST)
    @ResponseBody
    public String addWallet(@RequestBody Map<String,Object>data,Principal principal, HttpSession session,Model model)throws RazorpayException

    {
      if(principal==null)
      {
          return "redirect:/login";
      }
      Customer customer=customerService.findByEmail(principal.getName());
      double amount=Double.parseDouble(data.get("amount").toString());
        WalletHistory walletHistory=walletService.save(amount,customer);
       String walletHistroyId=walletHistory.getId().toString();
       session.setAttribute("walletHistoryId",walletHistory.getId());
       model.addAttribute("success","money added successfully");
        RazorpayClient razorpayClient= new RazorpayClient("rzp_test_a6FWUUwQsh1e3M","hugBadNfp6CQBpm8xiBl40yI");

        JSONObject options=new JSONObject();
        options.put("amount",amount*100);
        options.put("currency","INR");
        options.put("receipt",walletHistroyId);
        com.razorpay.Order orderRazorPay=razorpayClient.Orders.create(options);
        return orderRazorPay.toString();
    }
 @RequestMapping(value = "/verify-wallet",method = RequestMethod.POST)
    @ResponseBody
    public  String verifyWallet(@RequestBody Map<String,Object>data,Principal principal,HttpSession session) throws RazorpayException {

     String secrect="hugBadNfp6CQBpm8xiBl40yI";
     String order_id=data.get("razorpay_order_id").toString();
     String payment_id=data.get("razorpay_payment_id").toString();
     String order_signature=data.get("razorpay_signature").toString();
      JSONObject options=new JSONObject();
      options.put("razorpay_order_id",order_id);
      options.put("razorpay_payment_id",payment_id);
      options.put("razorpay_signature",order_signature);


      boolean status= Utils.verifyPaymentSignature(options,secrect);
     System.out.println(status);
      WalletHistory walletHistory= walletService.findById((Long)session.getAttribute("walletHistoryId"));
      Customer customer=customerService.findByEmail(principal.getName());
      if(status)
      {
          walletService.update(walletHistory,customer,status);
      }else {
          walletService.update(walletHistory,customer,status);
      }
      JSONObject response=new JSONObject();

      response.put("status",status);
      session.removeAttribute("walletHistoryId");
      return response.toString();

 }
}
