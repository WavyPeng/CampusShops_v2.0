package com.wavy.o2o.exception;
/**
 * 店铺种类异常类
 * 继承RuntimeException是为了事务回滚
 * Created by WavyPeng on 2018/6/29.
 */
public class ShopCategoryOperationException extends RuntimeException{

    private static final long serialVersionUID = -3033119601289473219L;

    public ShopCategoryOperationException(String msg){
        super(msg);
    }
}