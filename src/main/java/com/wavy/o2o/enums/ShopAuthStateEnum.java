package com.wavy.o2o.enums;

/**
 * 授权状态媒体类
 * Created by WavyPeng on 2018/7/21.
 */
public enum ShopAuthStateEnum {

    SUCCESS(1, "操作成功"),
    INNER_ERROR(-1001,"操作失败"),
    NULL_SHOPAUTH_ID(-1002,"ShopAuthId为空"),
    NULL_SHOPAUTH_INFO(-1003,"消息为空");

    private int state;
    private String stateInfo;

    ShopAuthStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopAuthStateEnum stateOf(int index){
        for (ShopAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
