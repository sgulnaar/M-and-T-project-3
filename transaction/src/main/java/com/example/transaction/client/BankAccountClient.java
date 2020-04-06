package com.example.transaction.client;

import com.example.transaction.bean.AccountDto;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="account", fallbackFactory = BankAccountFallbackFactory.class)
public interface BankAccountClient {

    @GetMapping("/v1/account/customer/{customerId}")
    AccountDto getCustomerAccount(@PathVariable String customerId);

    @PutMapping("/v1/account")
    AccountDto updateAccountBalance(@RequestBody AccountDto accountDto);
}
