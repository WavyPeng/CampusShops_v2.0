package com.wavy.o2o.enums;

/**
 * 奖品状态枚举类
 * Created by WavyPeng on 2018/7/23.
 */
public enum AwardStateEnum {
    SUCCESS(1, "操作成功"),
    EMPTY(1001, "信息为空");

    private int state;
    private String stateInfo;

    AwardStateEnum(int state, String stateInfo) {
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
    public static AwardStateEnum stateOf(int state){
        for(AwardStateEnum awardStateEnum:values()){
            if(state == awardStateEnum.getState()){
                return awardStateEnum;
            }
        }
        return null;
    }
}
