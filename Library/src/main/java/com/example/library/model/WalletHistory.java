package com.example.library.model;

import com.example.library.enums.WalletTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "wallet_history")
public class WalletHistory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "wallet_history_id")
private Long id;


private double amount;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "wallet_id",referencedColumnName = "wallet_id")
private Wallet wallet;

private WalletTransactionType type;

private String transactionStatus;

private LocalDate transactiondate;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "order_id",referencedColumnName = "order_id")
private Order order;

}
