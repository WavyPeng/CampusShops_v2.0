package com.wavy.o2o.exception;

/**
 * 头条异常类
 * 继承RuntimeException是为了事务回滚
 * Created by WavyPeng on 2018/6/29.
 */
public class HeadLineOperationException extends RuntimeException{
    private static final long serialVersionUID = 1374498314814097115L;

    public HeadLineOperationException(String msg){
        super(msg);
    }
}