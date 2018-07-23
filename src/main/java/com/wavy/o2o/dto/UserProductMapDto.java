package com.wavy.o2o.dto;

import com.wavy.o2o.entity.UserProductMap;
import com.wavy.o2o.enums.UserProductMapStateEnum;

import java.util.List;

/**
 * 用户-消费产品记录
 * Created by WavyPeng on 2018/7/23.
 */
public class UserProductMapDto {
    /**结果状态 */
    private int state;
    /**状态标识 */
    private String stateInfo;
    /**消费数目 */
    private Integer count;
    /**操作的userProductMap */
    private UserProductMap userProductMap;
    /**消费列表 */
    private List<UserProductMap> userProductMapList;

    public UserProductMapDto() {
    }

    /**
     * 失败时的构造器
     * @param userProductMapStateEnum
     */
    public UserProductMapDto(UserProductMapStateEnum userProductMapStateEnum) {
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
    }

    /**
     * 成功时的构造器
     * @param userProductMapStateEnum
     * @param userProductMap
     */
    public UserProductMapDto(UserProductMapStateEnum userProductMapStateEnum,UserProductMap userProductMap) {
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
        this.userProductMap = userProductMap;
    }

    /**
     * 成功时的构造器
     * @param userProductMapStateEnum
     * @param userProductMapList
     */
    public UserProductMapDto(UserProductMapStateEnum userProductMapStateEnum,List<UserProductMap> userProductMapList) {
        this.state = userProductMapStateEnum.getState();
        this.stateInfo = userProductMapStateEnum.getStateInfo();
        this.userProductMapList = userProductMapList;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public UserProductMap getUserProductMap() {
        return userProductMap;
    }

    public void setUserProductMap(UserProductMap userProductMap) {
        this.userProductMap = userProductMap;
    }

    public List<UserProductMap> getUserProductMapList() {
        return userProductMapList;
    }

    public void setUserProductMapList(List<UserProductMap> userProductMapList) {
        this.userProductMapList = userProductMapList;
    }
}
