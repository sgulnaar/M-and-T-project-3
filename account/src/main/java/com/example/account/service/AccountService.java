package com.example.account.service;

import com.example.account.bean.Account;
import com.example.account.bean.AccountDto;

public interface AccountService {
    AccountDto getCustomerAccount(String customerId);
    AccountDto updateAccountBalance(AccountDto accountDto);
    Account createAccount(Account account);
}
