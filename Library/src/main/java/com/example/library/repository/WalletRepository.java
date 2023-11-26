package com.example.library.repository;

import com.example.library.model.Wallet;
import com.example.library.model.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}