package com.adityasamant.learnings.restclient.exceptions;

public class CustomerServiceConnectionException extends RuntimeException {
    public CustomerServiceConnectionException() {
        super();
    }

    public CustomerServiceConnectionException(String message) {
        super(message);
    }
}
