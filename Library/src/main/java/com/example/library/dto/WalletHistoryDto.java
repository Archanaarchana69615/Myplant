package com.example.library.dto;

import com.example.library.enums.WalletTransactionType;
import com.example.library.model.Wallet;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletHistoryDto {

    private long id;

    @NotBlank
    private double amount;


    private Wallet wallet;

    private WalletTransactionType type;


    private String transactionStatus;

    private LocalDate transactionDate;

    private Long orderId;





}
