package com.wavy.o2o.exception;

/**
 * 授权异常类
 * Created by WavyPeng on 2018/7/21.
 */
public class ShopAuthMapOperationException extends RuntimeException{

    private static final long serialVersionUID = 5278378576551132105L;

    public ShopAuthMapOperationException(String message) {
        super(message);
    }
}
