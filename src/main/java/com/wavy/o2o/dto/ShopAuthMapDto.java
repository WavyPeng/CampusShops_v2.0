package com.wavy.o2o.dto;

import com.wavy.o2o.entity.ShopAuthMap;
import com.wavy.o2o.enums.ShopAuthStateEnum;

import java.util.List;

/**
 * 店铺授权
 * Created by WavyPeng on 2018/7/21.
 */
public class ShopAuthMapDto {
    /**结果状态 */
    private int state;
    /**状态标识 */
    private String stateInfo;
    /**授权数 */
    private Integer count;
    /**操作的shopAuthMap */
    private ShopAuthMap shopAuthMap;
    /**授权列表 */
    private List<ShopAuthMap> shopAuthMapList;

    public ShopAuthMapDto() {
    }

    /**
     * 失败是的构造器
     * @param shopAuthStateEnum
     */
    public ShopAuthMapDto(ShopAuthStateEnum shopAuthStateEnum){
        this.state = shopAuthStateEnum.getState();
        this.stateInfo = shopAuthStateEnum.getStateInfo();
    }

    /**
     * 成功时的构造器
     * @param shopAuthStateEnum
     * @param shopAuthMap
     */
    public ShopAuthMapDto(ShopAuthStateEnum shopAuthStateEnum,
                          ShopAuthMap shopAuthMap){
        this.state = shopAuthStateEnum.getState();
        this.stateInfo = shopAuthStateEnum.getStateInfo();
        this.shopAuthMap = shopAuthMap;
    }

    /**
     * 成功时的构造器
     * @param shopAuthStateEnum
     * @param shopAuthMapList
     */
    public ShopAuthMapDto(ShopAuthStateEnum shopAuthStateEnum,
                          List<ShopAuthMap> shopAuthMapList){
        this.state = shopAuthStateEnum.getState();
        this.stateInfo = shopAuthStateEnum.getStateInfo();
        this.shopAuthMapList = shopAuthMapList;
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

    public ShopAuthMap getShopAuthMap() {
        return shopAuthMap;
    }

    public void setShopAuthMap(ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
    }

    public List<ShopAuthMap> getShopAuthMapList() {
        return shopAuthMapList;
    }

    public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
    }
}
