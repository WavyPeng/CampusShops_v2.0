package com.wavy.o2o.dto;

import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.enums.ShopStateEnum;

import java.util.List;

/**
 * 店铺
 * Created by WavyPeng on 2018/6/3.
 */
public class ShopDto {
    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 店铺数量
    private int count;
    // 操作的shop(增删改店铺的时候用到)
    private Shop shop;
    // shop列表(查询店铺列表的时候使用)
    private List<Shop> shopList;

    public ShopDto() {
    }

    /**
     * 构造器
     * 用于店铺操作失败
     * @param shopStateEnum
     */
    public ShopDto(ShopStateEnum shopStateEnum) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }

    /**
     * 构造器
     * 用于店铺操作成功
     * @param shopStateEnum
     * @param shop
     */
    public ShopDto(ShopStateEnum shopStateEnum,Shop shop) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 构造器
     * 用于店铺操作成功
     * @param shopStateEnum
     * @param shopList
     */
    public ShopDto(ShopStateEnum shopStateEnum,List<Shop> shopList) {
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
