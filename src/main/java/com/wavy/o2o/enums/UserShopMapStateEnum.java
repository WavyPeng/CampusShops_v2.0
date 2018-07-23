package com.wavy.o2o.enums;

/**
 * 用户-店铺枚举类
 * Created by WavyPeng on 2018/7/23.
 */
public enum UserShopMapStateEnum {
    SUCCESS(1, "操作成功");

    private int state;
    private String stateInfo;

    UserShopMapStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     * @param state
     * @return
     */
    public static UserShopMapStateEnum stateOf(int state){
        for(UserShopMapStateEnum userShopMapStateEnum:values()){
            if(state == userShopMapStateEnum.getState()){
                return userShopMapStateEnum;
            }
        }
        return null;
    }
}
