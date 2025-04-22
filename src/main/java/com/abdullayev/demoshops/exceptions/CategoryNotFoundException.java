package com.abdullayev.demoshops.exceptions;

public class CategoryNotFoundException  extends RuntimeException{
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
