package com.wavy.o2o.dto;

import com.wavy.o2o.entity.UserShopMap;
import com.wavy.o2o.enums.UserShopMapStateEnum;
import java.util.List;

/**
 * 用户-店铺映射实体类
 * Created by WavyPeng on 2018/7/23.
 */
public class UserShopMapDto {
    /**结果状态 */
    private int state;
    /**状态标识 */
    private String stateInfo;
    /**数目 */
    private Integer count;
    /**操作的userProductMap */
    private UserShopMap userProductMap;
    /**用户在某商店消费积分列表 */
    private List<UserShopMap> userProductMapList;

    public UserShopMapDto() {
    }

    /**
     * 失败时的构造器
     * @param userShopMapStateEnum
     */
    public UserShopMapDto(UserShopMapStateEnum userShopMapStateEnum){
        this.state = userShopMapStateEnum.getState();
        this.stateInfo = userShopMapStateEnum.getStateInfo();
    }

    /**
     * 成功时的构造器
     * @param userShopMapStateEnum
     * @param userProductMap
     */
    public UserShopMapDto(UserShopMapStateEnum userShopMapStateEnum,UserShopMap userProductMap){
        this.state = userShopMapStateEnum.getState();
        this.stateInfo = userShopMapStateEnum.getStateInfo();
    }

    /**
     * 成功时的构造器
     * @param userShopMapStateEnum
     * @param userShopMapList
     */
    public UserShopMapDto(UserShopMapStateEnum userShopMapStateEnum,List<UserShopMap> userShopMapList){
        this.state = userShopMapStateEnum.getState();
        this.stateInfo = userShopMapStateEnum.getStateInfo();
        this.userProductMapList = userShopMapList;
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

    public UserShopMap getUserProductMap() {
        return userProductMap;
    }

    public void setUserProductMap(UserShopMap userProductMap) {
        this.userProductMap = userProductMap;
    }

    public List<UserShopMap> getUserProductMapList() {
        return userProductMapList;
    }

    public void setUserProductMapList(List<UserShopMap> userProductMapList) {
        this.userProductMapList = userProductMapList;
    }
}
