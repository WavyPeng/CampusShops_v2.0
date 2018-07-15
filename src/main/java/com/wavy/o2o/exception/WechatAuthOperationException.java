package com.wavy.o2o.exception;

public class WechatAuthOperationException extends RuntimeException {
    private static final long serialVersionUID = -6865773532504938463L;
    public WechatAuthOperationException(String msg) {
        super(msg);
    }
}