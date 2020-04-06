package com.example.transaction.exception;

public class NoAccountExistsException extends RuntimeException {
    public NoAccountExistsException(String message) {
        super(message);
    }

    public NoAccountExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
