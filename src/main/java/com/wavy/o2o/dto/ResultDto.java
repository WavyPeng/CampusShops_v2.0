package com.wavy.o2o.dto;

/**
 * 返回结果类
 * Created by WavyPeng on 2018/6/11.
 */
public class ResultDto<T> {
    private boolean success; // 是否成功标志
    private T data; // 成功时返回的数据
    private String errorMsg; // 错误信息
    private int errorCode; // 错误代码

    public ResultDto() {
    }

    // 成功时的构造器
    public ResultDto(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // 失败时的构造器

    public ResultDto(boolean success, String errorMsg, int errorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}