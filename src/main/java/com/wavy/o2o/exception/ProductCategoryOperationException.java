package com.wavy.o2o.exception;

/**
 * 商品种类异常类
 * 继承RuntimeException是为了事务回滚
 * Created by WavyPeng on 2018/6/11.
 */
public class ProductCategoryOperationException extends RuntimeException{

    private static final long serialVersionUID = -6370581861113425042L;

    public ProductCategoryOperationException(String message) {
        super(message);
    }
}