package com.wavy.o2o.exception;
/**
 * 地区异常类
 * 继承RuntimeException是为了事务回滚
 * Created by WavyPeng on 2018/6/29.
 */
public class AreaOperationException extends RuntimeException{

    private static final long serialVersionUID = 7306377669536501806L;

    public AreaOperationException(String msg){
        super(msg);
    }
}