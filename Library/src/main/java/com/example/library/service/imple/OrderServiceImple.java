package com.example.library.service.imple;

import com.example.library.model.*;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.ProductRepository;
import com.example.library.service.AddressService;
import com.example.library.repository.OrderDetailRepository;
import com.example.library.service.OrderService;
import com.example.library.service.ShoppingCartService;
import com.example.library.service.WalletService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImple implements OrderService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressService addressService;
    private final ShoppingCartService shoppingCartService;

    private final WalletService walletService;

    public OrderServiceImple(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository, AddressService addressService, ShoppingCartService shoppingCartService, WalletService walletService) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressService = addressService;
        this.shoppingCartService = shoppingCartService;
        this.walletService = walletService;
    }

    @Override
    public void updateOrderStatus(String status, long order_id) {
        if (order_id != 0) {
            Order order = orderRepository.getReferenceById(order_id);

            if (!order.getOrderStatus().equals("Returned") || !order.getOrderStatus().equals("Canceled")) {
                if (status.equals("Shipped")) {
                    order.setShippedDate(LocalDateTime.now());
                    order.setOrderStatus(status);
                    orderRepository.save(order);
                } else if (status.equals("Delivered")) {
                    order.setDeliveredDateTime(LocalDateTime.now());
                    order.setOrderStatus(status);
                    if (order.getPayementMethod().equals("COD")) {
                        order.setPayementStatus("paid");
                    }
                    orderRepository.save(order);
                }
            } else {
                System.out.println("order is already marked as returned");
            }
        }
    }

    @Override
    public List<Order> findAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public void acceptOrder(Long id) {
        Order order = orderRepository.getReferenceById(id);
        order.setAccept(true);
        Date oldDate = new Date();
        LocalDate localDate = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate newLocalDate = localDate.plusWeeks(1);
        Date newDate = Date.from(newLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setDeliverydate(newDate);
        order.setConfirmedDateTime(LocalDateTime.now());
        order.setOrderStatus("confirmed");
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(long id) {
        Order order = orderRepository.getReferenceById(id);
        Customer customer = order.getCustomer();
        List<OrderDetails> orderDetailList = order.getOrderDetailList();
        for (OrderDetails orderDetail : orderDetailList) {
            Product product = orderDetail.getProduct();
            if (product != null) {
                int currentQuantity = product.getCurrentQuantity();
                product.setCurrentQuantity(currentQuantity + orderDetail.getQuantity());
                productRepository.save(product);

            }
        }
        order.setOrderStatus("Cancelled");
        orderRepository.save(order);
        if(order.getPayementMethod().equals("Wallet")||order.getPayementMethod().equals("Razorpay"))
        {
          walletService.returnCredit(order,customer);
        }

    }



    @Override
    public Order findOrderById(long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public String getOrderStatus(Long id) {
        Order order = orderRepository.getReferenceById(id);
        String orderStatus = order.getOrderStatus();
        return orderStatus;
    }


    @Override
    public Order save(ShoppingCart cart, long address_id, String paymentMethod,Double OldTotalPrice) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setQuantity(cart.getTotalItems());
        order.setPayementMethod(paymentMethod);
        order.setShippingAddress(addressService.findByIdOrder(address_id));
        order.setAccept(false);
        order.setOrderStatus("Pending");
        if(OldTotalPrice != null){
            Double discount= OldTotalPrice - cart.getTotalPrice();
            String formattedDiscount = String.format("%.2f", discount);
            order.setDiscountPrice(Double.parseDouble(formattedDiscount));
        }

        List<OrderDetails> orderDetailList = new ArrayList<>();
        List<String> outOfStockItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            int currentQuantity = product.getCurrentQuantity();
            if (currentQuantity >= item.getQuantity()) {
                product.setCurrentQuantity(currentQuantity - item.getQuantity());
                productRepository.save(product);
                OrderDetails orderDetail = new OrderDetails();
                orderDetail.setOrder(order);
                orderDetail.setProduct(item.getProduct());
                orderDetail.setQuantity(item.getQuantity());
                orderDetailRepository.save(orderDetail);
                orderDetailList.add(orderDetail);
            } else {
                outOfStockItems.add(item.getProduct().getName());

                // Handle the case where the item is out of stock (optional)
                // You can choose to skip this item or handle it in any other way.
                // For example, you can add it to a separate list for out-of-stock items.
                // Here, we are simply skipping the item.
                continue;
            }
        }
        if (!outOfStockItems.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("The following items are not available in sufficient quantity: ");
            errorMessage.append(String.join(", ", outOfStockItems));

        }

        if(paymentMethod.equals("COD")) {
            order.setPayementStatus("Pending");
            shoppingCartService.deleteCartById(cart.getId());
        }else if(paymentMethod.equals("Wallet")){
            order.setPayementStatus("Paid");
            shoppingCartService.deleteCartById(cart.getId());
        }

//        order.setOrderDetailList(orderDetailList);
//        order.setPayementStatus("pending");
//        shoppingCartService.deleteCartById(cart.getId());

//        if (paymentMethod.equals("COD")) {
//            order.setPayementStatus("pending");
//            shoppingCartService.deleteCartById(cart.getId());
//        } else {
//            // Handle other payment methods if needed
//            // For payment methods other than COD, you can add custom logic here.
//            // For now, we are setting payment status to "Pending" for all other methods.
//            order.setPayementStatus("Pending");
//            shoppingCartService.deleteCartById(cart.getId());
//        }
        return orderRepository.save(order);
    }

    @Override
    public void updatePayment(Order order, boolean status)
    {
        if(status){
            order.setPayementStatus("paid");
            orderRepository.save(order);
        }else{
            order.setPayementStatus("Failed");
            orderRepository.save(order);
    }
}


}
