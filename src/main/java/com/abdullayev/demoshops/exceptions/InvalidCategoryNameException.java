package com.abdullayev.demoshops.exceptions;

public class InvalidCategoryNameException extends RuntimeException {
    public InvalidCategoryNameException(String message) {
        super(message);
    }
}
