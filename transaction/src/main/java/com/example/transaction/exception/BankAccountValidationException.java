package com.example.transaction.exception;

public class BankAccountValidationException extends RuntimeException {
    public BankAccountValidationException(String message) {
        super(message);
    }

    public BankAccountValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
