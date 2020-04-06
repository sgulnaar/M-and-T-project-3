package com.example.transaction.client;

import com.example.transaction.bean.AccountDto;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BankAccountFallbackFactory implements FallbackFactory<BankAccountClient> {
    @Override
    public BankAccountClient create(Throwable cause) {
        return new BankAccountFallbackService(cause);
    }
}
