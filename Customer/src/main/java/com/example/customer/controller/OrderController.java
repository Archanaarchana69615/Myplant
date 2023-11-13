package com.example.customer.controller;

import com.example.library.dto.AddressDto;
import com.example.library.model.Address;
import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    private OrderDetailRepository orderDetailRepository;


    public OrderController(CustomerService customerService, ShoppingCartService shoppingCartService, OrderService orderService, AddressService addressService, Categoryservice categoryservice, OrderDetailRepository orderDetailRepository) {
        this.customerService = customerService;
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.categoryservice = categoryservice;
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
            return "add-ad2dress";
        }else{
            Address address=new Address();
            model.addAttribute("addressEnter", address);
            model.addAttribute("addresses",customer.getAddresses());
            model.addAttribute("customer", customer);


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


@RequestMapping(value = "/add-order",method = RequestMethod.POST)
@ResponseBody
public String createOrder(@RequestBody Map<String, Object> data, Principal principal, HttpSession session, Model model) throws JSONException {
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
    }else {
        //return an error message for invalid payment

        JSONObject errorResponse= new JSONObject();
        errorResponse.put("error","invalid payment method");
        return errorResponse.toString();
    }
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
        return "redirect:/order-confirmation";
    }
}


