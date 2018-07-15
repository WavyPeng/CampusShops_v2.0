package com.wavy.o2o.dto;

import com.wavy.o2o.entity.WechatAccount;
import com.wavy.o2o.enums.WechatAuthStateEnum;

import java.util.List;

/**
 * 微信号信息
 * Created by WavyPeng on 2018/6/28.
 */
public class WechatAuthDto {
    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    private int count;
    private WechatAccount wechatAccount;
    private List<WechatAccount> wechatAccountList;

    public WechatAuthDto() {
    }

    // 失败时调用的构造器
    public WechatAuthDto(WechatAuthStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    // 成功的构造器
    public WechatAuthDto(WechatAuthStateEnum stateEnum, WechatAccount wechatAccount) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.wechatAccount = wechatAccount;
    }

    // 成功的构造器
    public WechatAuthDto(WechatAuthStateEnum stateEnum,
                               List<WechatAccount> wechatAccountList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.wechatAccountList = wechatAccountList;
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

    public WechatAccount getWechatAccount() {
        return wechatAccount;
    }

    public void setWechatAccount(WechatAccount wechatAccount) {
        this.wechatAccount = wechatAccount;
    }

    public List<WechatAccount> getWechatAccountList() {
        return wechatAccountList;
    }

    public void setWechatAccountList(List<WechatAccount> wechatAccountList) {
        this.wechatAccountList = wechatAccountList;
    }
}