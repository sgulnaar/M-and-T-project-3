package com.example.transaction.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ApiModel(description = "All details about the transaction.")
public class TransactionDto {
    @ApiModelProperty(notes = "The id of the transaction", position = 0)
    private String id;

    @ApiModelProperty(notes = "The id of the account", position = 0, required = true)
    @NotBlank(message = "Please provide customer id")
    private String customerId;

    @ApiModelProperty(notes = "The id of the customer", position = 1, required = true)
    @NotBlank(message = "Please provide customer id")
    private String accountId;

    @ApiModelProperty(notes = "The amount for the transaction", position = 2, required = true)
    @Positive(message = "Please provide valid transaction amount")
    private Double amount;

    @ApiModelProperty(notes = "Type of the transaction", position = 3)
    private TransactionType transactionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
