package com.example.account.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "All details about the account.")
public class AccountDto {
    @ApiModelProperty(notes = "The id of the account", position = 0)
    private String id;
    @ApiModelProperty(notes = "The id of the customer", position = 1)
    private String customerId;
    @ApiModelProperty(notes = "The account balance", position = 2)
    private Double balance;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
