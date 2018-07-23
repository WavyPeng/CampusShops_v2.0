package com.wavy.o2o.enums;

/**
 * 用户-消费商品枚举类
 * Created by WavyPeng on 2018/7/23.
 */
public enum UserProductMapStateEnum {

    SUCCESS(1, "操作成功");

    private int state;
    private String stateInfo;

    UserProductMapStateEnum(int state,String stateInfo){
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
    public static UserProductMapStateEnum stateOf(int state){
        for(UserProductMapStateEnum userProductMapStateEnum:values()){
            if(state == userProductMapStateEnum.getState()){
                return userProductMapStateEnum;
            }
        }
        return null;
    }

}
