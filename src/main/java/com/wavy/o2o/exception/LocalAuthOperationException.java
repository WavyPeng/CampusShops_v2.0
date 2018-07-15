package com.wavy.o2o.exception;

public class LocalAuthOperationException extends RuntimeException {

    private static final long serialVersionUID = 5926802725996616556L;
    public LocalAuthOperationException(String msg){
        super(msg);
    }
}