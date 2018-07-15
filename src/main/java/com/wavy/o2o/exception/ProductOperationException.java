package com.wavy.o2o.exception;

public class ProductOperationException extends RuntimeException{

    private static final long serialVersionUID = -112992116260384731L;

    public ProductOperationException(String message) {
        super(message);
    }
}