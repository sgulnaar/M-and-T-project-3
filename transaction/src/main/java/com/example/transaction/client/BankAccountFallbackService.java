package com.example.transaction.client;

import com.example.transaction.bean.AccountDto;
import com.example.transaction.exception.AccountUpdateException;
import com.example.transaction.exception.ServiceUnavailableException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankAccountFallbackService implements BankAccountClient {
    private static final Logger LOG = LoggerFactory.getLogger(BankAccountFallbackService.class);

    private final Throwable cause;

    public BankAccountFallbackService(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public AccountDto getCustomerAccount(String customerId) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            LOG.error("BankAccountFallbackService invoked as AccountService is down", cause);
            throw new ServiceUnavailableException("AccountService.getCustomerAccount API is down");
        } else {
            LOG.error("BankAccountFallbackService invoked as there is an issue with AccountService communication with getCustomerAccount API for customer {}", customerId);
            AccountDto accountDto = new AccountDto();
            accountDto.setCustomerId(customerId);
            return accountDto;
        }
    }

    @Override
    public AccountDto updateAccountBalance(AccountDto accountDto) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            LOG.error("BankAccountFallbackService invoked as AccountService is down", cause);
            throw new ServiceUnavailableException("AccountService.updateAccountBalance API is down");
        } else {
            LOG.error("BankAccountFallbackService invoked as there is an issue with AccountService communication with updateAccountBalance API for account {}", accountDto);
            throw new AccountUpdateException("UpdateAccountBalance is unsuccessful");
        }
    }
}
