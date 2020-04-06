package com.example.transaction.service.impl;

import com.example.transaction.bean.AccountDto;
import com.example.transaction.bean.Transaction;
import com.example.transaction.bean.TransactionDto;
import com.example.transaction.bean.TransactionType;
import com.example.transaction.client.BankAccountClient;
import com.example.transaction.exception.AccountUpdateException;
import com.example.transaction.exception.BankAccountValidationException;
import com.example.transaction.exception.InsufficientBalanceException;
import com.example.transaction.exception.NoAccountExistsException;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountClient bankAccountClient;

    @Override
    public TransactionDto withdraw(TransactionDto transactionDto) {
        String customerId = transactionDto.getCustomerId();
        String accountId = transactionDto.getAccountId();
        Double amount = transactionDto.getAmount();

        AccountDto account = validateCustomerAccount(accountId, customerId);

        validateAccountBalance(account, amount);

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setCustomerId(customerId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEBIT);

        Transaction transactionPersisted = transactionRepository.save(transaction);
        transactionDto.setId(transactionPersisted.getId());

        updateAccount(TransactionType.DEBIT, amount, account);
        transactionDto.setTransactionType(TransactionType.DEBIT);
        return transactionDto;
    }

    @Override
    public TransactionDto deposit(TransactionDto transactionDto) {
        String customerId = transactionDto.getCustomerId();
        String accountId = transactionDto.getAccountId();
        Double amount = transactionDto.getAmount();

        AccountDto account = validateCustomerAccount(accountId, customerId);

        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setCustomerId(customerId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CREDIT);

        Transaction transactionPersisted = transactionRepository.save(transaction);
        transactionDto.setId(transactionPersisted.getId());

        updateAccount(TransactionType.CREDIT, amount, account);
        transactionDto.setTransactionType(TransactionType.CREDIT);
        return transactionDto;
    }

    private void updateAccount(TransactionType transactionType, Double amount, AccountDto accountDto) {
        switch (transactionType) {
            case DEBIT:
                accountDto.setBalance(accountDto.getBalance() - amount);
                break;
            case CREDIT:
                accountDto.setBalance(accountDto.getBalance() + amount);
                break;
            default:
                throw new AccountUpdateException("An unknown account update transaction.");
        }
        try {
            bankAccountClient.updateAccountBalance(accountDto);
        } catch (Exception e) {
            throw new AccountUpdateException("An unknown error occurred during account update.", e);
        }
    }

    private AccountDto validateCustomerAccount(String accountId, String customerId){
        //getCustomer(customerId);
        AccountDto account = getCustomerAccount(customerId);
        if(!Objects.equals(account.getId(), accountId)) {
            throw new BankAccountValidationException(MessageFormat.format("Invalid access to account {0} from customer {1}", accountId, customerId));
        }
        return account;
    }

    private AccountDto getCustomerAccount(String customerId) {
        try {
            LOG.info(String.format("Fetching account for customer: %s", customerId));
            AccountDto bankAccountDto = bankAccountClient.getCustomerAccount(customerId);
            if (bankAccountDto != null) {
                return bankAccountDto;
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new BankAccountValidationException("An unknown error occurred while attempting to validate the account.", e);
        }
        throw new NoAccountExistsException(String.format("No account with id %s exists", customerId));
    }

    private void validateAccountBalance(AccountDto account, Double amount) {
        if (account.getBalance().compareTo(amount) < 1) {
            throw new InsufficientBalanceException("Insufficient balance on account.");
        }
    }

    /*private CustomerDto getCustomer(String customerId) {
        try {
            CustomerDto customer =  bankCustomerClient.get(customerId);
            if (customer != null) {
                return customer;
            }
        } catch (Exception e) {
            throw new CustomerValidationException("An unknown error occurred while attempting to validate the account owner.", e);
        }
        throw new NoCustomerExistsException(String.format("No customer with id %s exists", customerId));
    }*/
}
