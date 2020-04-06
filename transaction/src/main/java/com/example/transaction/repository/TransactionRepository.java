package com.example.transaction.repository;

import com.example.transaction.bean.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    @Query(value = "{'customerId' : ?0}")
    List<Transaction> findByCustomerId(String customerId);
}
