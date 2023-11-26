package com.example.library.service;

//import com.example.library.dto.WalletHistoryDto;
import com.example.library.dto.WalletHistoryDto;
import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.Wallet;
import com.example.library.model.WalletHistory;
//import com.example.library.model.Wallet;

import java.util.List;

public interface WalletService {

    List<WalletHistoryDto> findAllById(long id);
    List<WalletHistoryDto> findAllByWalletId(long walletId);

    Wallet findByCustomer(Customer customer);

    WalletHistory save(double amount,Customer customer);

    WalletHistory findById(long id);

    void update(WalletHistory walletHistory,Customer customer,boolean status);

    WalletHistory debit(Wallet wallet,double totalprice);

    void returnCredit(Order order,Customer customer);

    void saveOrderId(Order order,WalletHistory walletHistory);


}


