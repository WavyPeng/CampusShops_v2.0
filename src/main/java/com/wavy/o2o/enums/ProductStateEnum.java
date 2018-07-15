package com.wavy.o2o.enums;

/**
 * 商品状态枚举类
 * Created by WavyPeng on 2018/6/11.
 */
public enum ProductStateEnum {
    OFFLINE(-1, "非法商品"),
    DOWN(0, "下架"),
    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY(-1002, "商品为空");

    private int state;
    private String stateInfo;

    ProductStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductStateEnum stateOf(int index) {
        for (ProductStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}