package com.example.account.repository;

import com.example.account.bean.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query(value = "{'customerId' : ?0}")
    Account findByCustomerId(String customerId);
}

