/*package com.example.account.service.impl;

import com.example.account.bean.Account;
import com.example.account.bean.AccountDto;
import com.example.account.exception.AccountReadException;
import com.example.account.repository.AccountRepository;
import com.example.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    private int hystrixTestCounter = 1;

    private void prepareDto(Account account, AccountDto accountDto) {
        accountDto.setId(account.getId());
        accountDto.setCustomerId(account.getCustomerId());
        accountDto.setBalance(account.getBalance());
    }

    @Override
    public AccountDto getCustomerAccount(String customerId) {
        Account account = getAccount(customerId);
        AccountDto accountDto = new AccountDto();
        prepareDto(account, accountDto);
        return accountDto;
    }

    @Transactional
    @Override
    public AccountDto updateAccountBalance(AccountDto accountDto) {
        Account account = getAccount(accountDto.getCustomerId());
        account.setBalance(accountDto.getBalance());
        accountRepository.save(account);
        return accountDto;
    }

    public Account getAccount(String customerId) {
        if(hystrixTestCounter++ % 3 == 0) {
            throw new AccountReadException("test exception to verify hystrix");
        }
        return accountRepository.findByCustomerId(customerId);
    }

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @PostConstruct
    public void createDummyAccount() {
        LOG.info("inserting dummy accounts");
        List<Account> accounts = new ArrayList<>();
        Account accountFound = accountRepository.findByCustomerId("5e80bc90bfaa6a5bb73e44fa");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc90bfaa6a5bb73e44fa");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fb");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fb");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fc");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fc");
            account.setBalance(100);
            accounts.add(account);
        }
        accounts.forEach(this::createAccount);
        LOG.info("dummy accounts inserted");
    }
}


/*
package com.example.account.service.impl;

import com.example.account.bean.Account;
import com.example.account.bean.AccountDto;
import com.example.account.exception.AccountReadException;
import com.example.account.repository.AccountRepository;
import com.example.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    private void prepareDto(Account account, AccountDto accountDto) {
        accountDto.setId(account.getId());
        accountDto.setCustomerId(account.getCustomerId());
        accountDto.setBalance(account.getBalance());
    }

    @Override
    public AccountDto getCustomerAccount(String customerId) {
        Account account = getAccount(customerId);
        AccountDto accountDto = new AccountDto();
        prepareDto(account, accountDto);
        return accountDto;
    }

    @Transactional
    @Override
    public AccountDto updateAccountBalance(AccountDto accountDto) {
        Account account = getAccount(accountDto.getCustomerId());
        account.setBalance(accountDto.getBalance());
        accountRepository.save(account);
        return accountDto;
    }

    public Account getAccount(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @PostConstruct
    public void createDummyAccount() {
        LOG.info("inserting dummy accounts");
        List<Account> accounts = new ArrayList<>();
        Account accountFound = accountRepository.findByCustomerId("5e80bc90bfaa6a5bb73e44fa");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc90bfaa6a5bb73e44fa");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fb");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fb");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fc");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fc");
            account.setBalance(100);
            accounts.add(account);
        }
        accounts.forEach(this::createAccount);
        LOG.info("dummy accounts inserted");
    }
}
*/

package com.example.account.service.impl;

import com.example.account.bean.Account;
import com.example.account.bean.AccountDto;
import com.example.account.exception.AccountReadException;
import com.example.account.repository.AccountRepository;
import com.example.account.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    private void prepareDto(Account account, AccountDto accountDto) {
        accountDto.setId(account.getId());
        accountDto.setCustomerId(account.getCustomerId());
        accountDto.setBalance(account.getBalance());
    }

    @Override
    public AccountDto getCustomerAccount(String customerId) {
        Account account = getAccount(customerId);
        AccountDto accountDto = new AccountDto();
        prepareDto(account, accountDto);
        return accountDto;
    }

    @Transactional
    @Override
    public AccountDto updateAccountBalance(AccountDto accountDto) {
        Account account = getAccount(accountDto.getCustomerId());
        account.setBalance(accountDto.getBalance());
        accountRepository.save(account);
        return accountDto;
    }

    public Account getAccount(String customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Transactional
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @PostConstruct
    public void createDummyAccount() {
        LOG.info("inserting dummy accounts");
        List<Account> accounts = new ArrayList<>();
        Account accountFound = accountRepository.findByCustomerId("5e80bc90bfaa6a5bb73e44fa");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc90bfaa6a5bb73e44fa");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fb");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fb");
            account.setBalance(100);
            accounts.add(account);
        }
        accountFound = accountRepository.findByCustomerId("5e80bc91bfaa6a5bb73e44fc");
        if(Objects.isNull(accountFound)) {
            Account account = new Account();
            account.setCustomerId("5e80bc91bfaa6a5bb73e44fc");
            account.setBalance(100);
            accounts.add(account);
        }
        accounts.forEach(this::createAccount);
        LOG.info("dummy accounts inserted");
    }
}
