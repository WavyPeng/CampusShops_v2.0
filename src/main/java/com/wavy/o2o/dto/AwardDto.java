package com.wavy.o2o.dto;

import com.wavy.o2o.entity.Award;
import com.wavy.o2o.enums.AwardStateEnum;

import java.util.List;

/**
 * 奖品
 * Created by WavyPeng on 2018/7/23.
 */
public class AwardDto {
    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 奖品数量
    private int count;
    // 操作的award(增删改店铺的时候用到)
    private Award award;
    // award列表(查询奖品列表的时候使用)
    private List<Award> awardList;

    public AwardDto() {
    }

    public AwardDto(AwardStateEnum awardStateEnum) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
    }

    public AwardDto(AwardStateEnum awardStateEnum,Award award) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
        this.award = award;
    }

    public AwardDto(AwardStateEnum awardStateEnum,List<Award> awardList) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
        this.awardList = awardList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<Award> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<Award> awardList) {
        this.awardList = awardList;
    }
}
