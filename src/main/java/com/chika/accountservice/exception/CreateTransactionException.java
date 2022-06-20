package com.chika.accountservice.exception;

public class CreateTransactionException extends RuntimeException{
    public CreateTransactionException(String message) {
        super(message);
    }
}
