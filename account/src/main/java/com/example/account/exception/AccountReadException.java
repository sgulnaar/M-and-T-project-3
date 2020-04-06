package com.example.account.exception;

public class AccountReadException extends RuntimeException {
    public AccountReadException(String message) {
        super(message);
    }

    public AccountReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
