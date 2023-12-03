package com.example.customer.controller;

import com.example.library.dto.AddressDto;
import com.example.library.dto.CouponDto;
import com.example.library.model.*;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.service.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    private CustomerService customerService;
    private ShoppingCartService shoppingCartService;
    private OrderService orderService;
    private AddressService addressService;

    private Categoryservice categoryservice;

    private CouponService couponService;

    private WalletService walletService;
    private OrderDetailRepository orderDetailRepository;


    public OrderController(CustomerService customerService, ShoppingCartService shoppingCartService, OrderService orderService, AddressService addressService, Categoryservice categoryservice, CouponService couponService, WalletService walletService, OrderDetailRepository orderDetailRepository) {
        this.customerService = customerService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.categoryservice = categoryservice;
        this.couponService = couponService;
        this.walletService = walletService;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping("/order")
    public String order(Principal principal, Model model)
    {
        if(principal==null)
        {
            return "redirect:/login";
        }
        String username=principal.getName();
        Customer customer =customerService.findByEmail(username);
        List<Order>orderList= customer.getOrders();
        model.addAttribute("orders",orderList);

        return "order";
    }
    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest){
        if(principal == null){
            return "redirect:/login";
        }
//        else (address==null)
//        {
//            return "redirect:/add-address";
//
//        }

        if (redirectAttributes.getFlashAttributes().containsKey("errorMessage")) {
            String errorMessage = (String) redirectAttributes.getFlashAttributes().get("errorMessage");
            model.addAttribute("errorMessage", errorMessage);
        }


        AddressDto addressDto=new AddressDto();
        model.addAttribute("addressDto", addressDto);

        String username = principal.getName();
        Customer customer = customerService.findByEmail(username);
        if(customer.getAddresses().isEmpty()){
            model.addAttribute("customer", customer);
            model.addAttribute("error", "You must fill the information before checkout!");
            return "add-address";
        }else{
            Address address=new Address();
            model.addAttribute("addressEnter", address);
            model.addAttribute("addresses",customer.getAddresses());
            model.addAttribute("customer", customer);

            Wallet wallet=walletService.findByCustomer(customer);
            model.addAttribute("wallet",wallet);
            List<Coupon>couponDto=couponService.getAllCoupons();
            model.addAttribute("coupons",couponDto);
            ShoppingCart cart = customer.getCart();

            model.addAttribute("cart", cart);
        }

        /*For the name*/
        HttpSession httpSession1= httpServletRequest.getSession();
        String name=null;
        if(httpSession1!=null) {
            name = httpServletRequest.getRemoteUser();
        }
        model.addAttribute("name",name);
        return "checkout";
    }

    @RequestMapping(value = "check-out/apply-coupon",method = RequestMethod.POST,params = "action=apply")
    public String applyCoupon(@RequestParam("couponCode")String couponCode,Principal principal,RedirectAttributes attributes,HttpSession session) {

        if (couponService.findByCouponCode(couponCode.toUpperCase())) {
            Coupon coupon = couponService.findByCode(couponCode.toUpperCase());
            ShoppingCart cart = customerService.findByEmail(principal.getName()).getCart();
            double totalPrice = cart.getTotalPrice();

            if (coupon.getMinOrderAmount()>totalPrice)
            {
              String message="minimum order amount to apply the coupon"+coupon.getCode()+"is"+ coupon.getMinOrderAmount();
              attributes.addFlashAttribute("minOrderAmount",message);

              return "redirect:/checkout";
            }
            session.setAttribute("totalPrice",totalPrice);
            Double NewTotalPrice=couponService.applyCoupon(couponCode.toUpperCase(),totalPrice);
            shoppingCartService.updateTotalPrice(NewTotalPrice,principal.getName());
             System.out.println(NewTotalPrice);
            attributes.addFlashAttribute("success","coupon applied successfully");
            attributes.addFlashAttribute("couponCode",couponCode);
            attributes.addFlashAttribute("couponOff",coupon.getOffPercentage());
        }else
        {
            attributes.addFlashAttribute("error","invalid couponCode");
        }
        return "redirect:/checkout";
    }
    @RequestMapping(value = "checkout/coupon-apply",method = RequestMethod.POST,params = "action=remove")
    public String RemoveCoupon(Principal principal,RedirectAttributes redirectAttributes,HttpSession session)
    {
      Double totalPrice=(Double) session.getAttribute("totalPrice");
      shoppingCartService.updateTotalPrice(totalPrice,principal.getName());
      redirectAttributes.addFlashAttribute("success","coupon removed successfully");

      return "redirect:/checkout";
    }

@RequestMapping(value = "/add-order",method = RequestMethod.POST)
@ResponseBody
public String createOrder(@RequestBody Map<String, Object> data, Principal principal, HttpSession session, Model model) throws RazorpayException, JSONException {
    String paymentMethod = data.get("payment_Method").toString();
    Long address_id = Long.parseLong(data.get("addressId").toString());
    Double oldTotalPrice = (Double) session.getAttribute("totalPrice");
    Customer customer = customerService.findByEmail(principal.getName());
    ShoppingCart cart = customer.getCart();

    if (paymentMethod.equals("COD")) {
        Order order = orderService.save(cart, address_id,paymentMethod,oldTotalPrice);
        session.removeAttribute("totalItems");
        session.removeAttribute("totalPrice");
        session.setAttribute("orderId", order.getId());
        model.addAttribute("order", order);
        model.addAttribute("page", "Order Detail");
        model.addAttribute("success", "Order Added Successfully");

        JSONObject options = new JSONObject();
        options.put("status", "cash");
        return options.toString();
    }
    else if(paymentMethod.equals("Wallet")){
        WalletHistory walletHistory=walletService.debit(customer.getWallet(), cart.getTotalPrice());


        Order order=orderService.save(cart,address_id,paymentMethod,oldTotalPrice);

        walletService.saveOrderId(order,walletHistory);

        session.removeAttribute("totalItems");
        session.removeAttribute("totalPrice");
        session.setAttribute("orderId",order.getId());
        model.addAttribute("order",order);
        model.addAttribute("page","order detail");
        model.addAttribute("success","order added successfully");

        JSONObject options=new JSONObject();
        options.put("status","Wallet");
        return options.toString();

    }else {
        Order order = orderService.save(cart, address_id, paymentMethod, oldTotalPrice);
        String orderId = order.getId().toString();
        session.removeAttribute("totalItems");
        session.removeAttribute("totalPrice");
        session.setAttribute("orderId", order.getId());
        model.addAttribute("order", order);
        model.addAttribute("page", "order detail");
        model.addAttribute("success", "order added successfully");

            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_a6FWUUwQsh1e3M", "hugBadNfp6CQBpm8xiBl40yI");
            JSONObject options = new JSONObject();
            options.put("amount", order.getTotalPrice() * 100);
            options.put("currency", "INR");
            options.put("receipt", orderId);
                com.razorpay.Order orderRazorpay = razorpayClient.Orders.create(options);
                return orderRazorpay.toString();
            }

            }
            @RequestMapping(value="/verify-payment",method={RequestMethod.POST,RequestMethod.GET})
            @ResponseBody
            public String verifyPayment(@RequestBody Map<String,Object> data,HttpSession session,Principal principal) throws RazorpayException, JSONException {
                System.out.println(data);
               String secret="hugBadNfp6CQBpm8xiBl40yI";
               String order_id=data.get("razorpay_order_id").toString();
               String payment_id=data.get("razorpay_payment_id").toString();
               String signature=data.get("razorpay_signature").toString();
               JSONObject options =new JSONObject();
               options.put("razorpay_order_id",order_id);
               options.put("razorpay_payment_id",payment_id);
               options.put("razorpay_signature",signature);


                boolean status= Utils.verifyPaymentSignature(options,secret);
               Order order=orderService.findOrderById((Long)session.getAttribute("orderId"));
               if(status)
               {
                 orderService.updatePayment(order,status);
                 Customer customer=customerService.findByEmail(principal.getName());
                 ShoppingCart cart=customer.getCart();
                 shoppingCartService.deleteCartById(cart.getId());
               }
               else {
                   orderService.updatePayment(order, status);
               }
                  JSONObject response= new JSONObject();
                  response.put("status",true);
                  return response.toString();

            }



    @GetMapping("/order-confirmation")
    public String getOrderConfirmation(Model model,HttpSession session){
        if(session.getAttribute("orderId")==null){
            return"redirect:/index";
        }
        Long order_id=(Long)session.getAttribute("orderId");
        Order order=orderService.findOrderById(order_id);
        String paymentMethod=order.getPayementMethod();
        if(paymentMethod.equals("COD")){
            model.addAttribute("payment","Pending");
        }else{
            model.addAttribute("payment","Successful");
        }
        model.addAttribute("orders",order);
        model.addAttribute("success","Order Added Successfully");
        session.removeAttribute("orderId");

        return "order";
    }
    @GetMapping("/cancel-order/{id}")
    public String cancelOrder(@PathVariable("id")long order_id,RedirectAttributes attributes){
        orderService.cancelOrder(order_id);
        attributes.addFlashAttribute("success","cancel order successfully");
        return "redirect:/account?tab=orders";
    }
    @GetMapping("/order/{id}")
    public String orderView(@PathVariable("id")long order_id,Model model,HttpSession session){
        Order order=orderService.findOrderById(order_id);
        String paymentMethod = order.getPayementMethod();
        if (paymentMethod.equals("COD")){
            model.addAttribute("payment","Pending");
        }else {
            model.addAttribute("payment", "Paid");
        }
        Customer customer=customerService.findById(order.getCustomer().getId());
        Address address = addressService.findDefaultAddress(customer.getId());
        model.addAttribute("order",order);
        model.addAttribute("address",address);

        return "order-view";
    }

}


