package com.wavy.o2o.enums;

/**
 * 微信号枚举类
 * Created by WavyPeng on 2018/6/28.
 */
public enum WechatAuthStateEnum {
    LOGINFAIL(-1, "openId输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "注册信息为空");

    private int state;

    private String stateInfo;

    private WechatAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static WechatAuthStateEnum stateOf(int index) {
        for (WechatAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}