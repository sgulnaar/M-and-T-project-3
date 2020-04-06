package com.example.transaction.service;

import com.example.transaction.bean.TransactionDto;

public interface TransactionService {
    TransactionDto withdraw(TransactionDto transactionDto);
    TransactionDto deposit(TransactionDto transactionDto);
}
