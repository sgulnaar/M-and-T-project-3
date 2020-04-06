package com.example.transaction.web.controller;

import com.example.transaction.bean.TransactionDto;
import com.example.transaction.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Transaction API")
@RestController
@RequestMapping("/v1/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @ApiOperation(value = "withdraw money from account", produces = "application/json")
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDto> withdraw(@RequestBody TransactionDto transactionDto) {
        TransactionDto transaction = transactionService.withdraw(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @ApiOperation(value = "deposit money to account", produces = "application/json")
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> deposit(@RequestBody TransactionDto transactionDto) {
        TransactionDto transaction = transactionService.deposit(transactionDto);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
