package com.example.library.service.imple;

//import com.example.library.dto.WalletHistoryDto;
import com.example.library.dto.WalletHistoryDto;
import com.example.library.enums.WalletTransactionType;
import com.example.library.model.Customer;
//import com.example.library.model.Wallet;
import com.example.library.model.Order;
import com.example.library.model.Wallet;
import com.example.library.model.WalletHistory;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.WalletHistoryRepository;
import com.example.library.repository.WalletRepository;
import com.example.library.service.WalletService;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletServiceImple implements WalletService {

    private WalletHistoryRepository walletHistoryRepository;

    private WalletRepository walletRepository;

    private OrderRepository orderRepository;

    private CustomerRepository customerRepository;

    public WalletServiceImple(WalletHistoryRepository walletHistoryRepository, WalletRepository walletRepository, OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.walletHistoryRepository = walletHistoryRepository;
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<WalletHistoryDto> findAllById(long id) {
        List<WalletHistory>walletHistories=walletHistoryRepository.findAllById(id);

        System.out.println(walletHistories);
        List<WalletHistoryDto>walletHistoryDtoList=transferData(walletHistories);
        return walletHistoryDtoList;
    }

    @Override
    public Wallet findByCustomer(Customer customer) {
    Wallet wallet;
    if(customer.getWallet()==null)
    {
      wallet=new Wallet();
      wallet.setBalance(0.0);
      wallet.setCustomer(customer);
      walletRepository.save(wallet);
    }else {
        wallet=customer.getWallet();
    }
        return wallet;
    }
    @Override
    public List<WalletHistoryDto> findAllByWalletId(long walletId) {
        List<WalletHistory> walletHistories = walletHistoryRepository.findAllByWalletId(walletId);

        System.out.println(walletHistories);

        List<WalletHistoryDto> walletHistoryDtoList = transferData(walletHistories);
        return walletHistoryDtoList;
    }


    @Override
    public WalletHistory save(double amount, Customer customer) {
     Wallet wallet=customer.getWallet();
     WalletHistory walletHistory=new WalletHistory();
     walletHistory.setWallet(wallet);
     walletHistory.setAmount(amount);
     walletHistory.setType(WalletTransactionType.TOPUP);
        LocalDate currentDate=LocalDate.now();
     walletHistory.setTransactiondate(currentDate);
     walletHistoryRepository.save(walletHistory);

        return walletHistory;
    }

    @Override
    public WalletHistory findById(long id) {
        WalletHistory walletHistory=walletHistoryRepository.findById(id);
        return walletHistory;
    }

    @Override
    public void update(WalletHistory walletHistory, Customer customer, boolean status) {
        Wallet wallet=customer.getWallet();
        if(status)
        {
         walletHistory.setTransactionStatus("success");
         walletHistoryRepository.save(walletHistory);
         double newBalance=wallet.getBalance()+ walletHistory.getAmount();
            DecimalFormat decimalFormat=new DecimalFormat("#0.00");
            String formatedBalance= decimalFormat.format(newBalance);
            double formatedDoubleBalance=Double.parseDouble(formatedBalance);
            wallet.setBalance(formatedDoubleBalance);

            walletRepository.save(wallet);
        }else {
           walletHistory.setTransactionStatus("failed");
           walletHistoryRepository.save(walletHistory);
        }

    }

    @Override
    public WalletHistory debit(Wallet wallet, double totalprice) {

        double newBalance=wallet.getBalance()-totalprice;
        DecimalFormat decimalFormat=new DecimalFormat("#0.00");
        String formatedBalance=decimalFormat.format(newBalance);
        double formatedDoubleBalance=Double.parseDouble(formatedBalance);
        wallet.setBalance(formatedDoubleBalance);

        walletRepository.save(wallet);
        WalletHistory walletHistory=new WalletHistory();
        walletHistory.setWallet(wallet);
        walletHistory.setType(WalletTransactionType.DEBITED);
        walletHistory.setAmount(totalprice);
        walletHistory.setTransactionStatus("success");
        LocalDate currentDate=LocalDate.now();
        walletHistory.setTransactiondate(currentDate);
        WalletHistory walletHistory1= walletHistoryRepository.save(walletHistory);
        return walletHistory1;
    }
    @Override
    public void returnCredit(Order order, Customer customer) {
        Wallet wallet=customer.getWallet();
        wallet.setBalance(wallet.getBalance()+order.getTotalPrice());
        walletRepository.save(wallet);
        WalletHistory walletHistory=new WalletHistory();
        walletHistory.setWallet(wallet);
        walletHistory.setType(WalletTransactionType.CREDITED);
        walletHistory.setTransactionStatus("success");
        walletHistory.setAmount(order.getTotalPrice());
        walletHistory.setOrder(order);
        LocalDate currentDate=LocalDate.now();
        walletHistory.setTransactiondate(currentDate);
        walletHistoryRepository.save(walletHistory);
    }

    @Override
    public void saveOrderId(Order order, WalletHistory walletHistory) {

        walletHistory.setOrder(order);
        walletHistoryRepository.save(walletHistory);
    }

    private List<WalletHistoryDto>transferData(List<WalletHistory>walletHistoryList)
    {
     List<WalletHistoryDto>walletHistoryDtoList=new ArrayList<>();

     for(WalletHistory walletHistory:walletHistoryList)
     {
      WalletHistoryDto walletHistoryDto=new WalletHistoryDto();
      walletHistoryDto.setId(walletHistory.getId());
      walletHistoryDto.setAmount(walletHistory.getAmount());
      walletHistoryDto.setTransactionDate(walletHistory.getTransactiondate());
      walletHistoryDto.setTransactionStatus(walletHistory.getTransactionStatus());
      walletHistoryDto.setWallet(walletHistory.getWallet());
      walletHistoryDto.setType(walletHistory.getType());

      Order order=walletHistory.getOrder();
      if(order!=null)
      {
       walletHistoryDto.setOrderId(order.getId());
      }
       walletHistoryDtoList.add(walletHistoryDto);
     }
   return walletHistoryDtoList;
    }

}
