package com.wavy.o2o.enums;

/**
 * 店铺状态枚举类
 * Created by WavyPeng on 2018/6/3.
 */
public enum ShopStateEnum {

    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002, "ShopId为空"),
    NULL_SHOP(-1003, "shop信息为空");

    private int state;
    private String stateInfo;

    ShopStateEnum(int state, String stateInfo) {
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
    public static ShopStateEnum stateOf(int state){
        for(ShopStateEnum shopStateEnum:values()){
            if(state == shopStateEnum.getState()){
                return shopStateEnum;
            }
        }
        return null;
    }
}
