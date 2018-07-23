package com.wavy.o2o.exception;

/**
 * Created by WavyPeng on 2018/7/23.
 */
public class AwardOperationException extends RuntimeException{
    private static final long serialVersionUID = -8995767207209785032L;

    public AwardOperationException(String message) {
        super(message);
    }
}
