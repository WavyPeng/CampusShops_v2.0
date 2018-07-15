package com.wavy.o2o.dto;

import com.wavy.o2o.entity.LocalAccount;
import com.wavy.o2o.enums.LocalAuthStateEnum;

import java.util.List;

/**
 * 本地账号信息
 * Created by WavyPeng on 2018/6/30.
 */
public class LocalAuthDto {
    // 结果状态
    private int state;
    // 状态信息
    private String stateInfo;
    private int count;
    private LocalAccount localAccount;
    private List<LocalAccount> localAccountList;

    public LocalAuthDto() {
    }

    // 失败时调用的构造器
    public LocalAuthDto(LocalAuthStateEnum localAuthStateEnum){
        this.state = localAuthStateEnum.getState();
        this.stateInfo = localAuthStateEnum.getStateInfo();
    }

    // 成功时调用的构造器
    public LocalAuthDto(LocalAuthStateEnum localAuthStateEnum,LocalAccount localAccount){
        this.state = localAuthStateEnum.getState();
        this.stateInfo = localAuthStateEnum.getStateInfo();
        this.localAccount = localAccount;
    }

    // 成功时调用的构造器
    public LocalAuthDto(LocalAuthStateEnum localAuthStateEnum,List<LocalAccount> localAccountList){
        this.state = localAuthStateEnum.getState();
        this.stateInfo = localAuthStateEnum.getStateInfo();
        this.localAccountList = localAccountList;
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

    public LocalAccount getLocalAccount() {
        return localAccount;
    }

    public void setLocalAccount(LocalAccount localAccount) {
        this.localAccount = localAccount;
    }

    public List<LocalAccount> getLocalAccountList() {
        return localAccountList;
    }

    public void setLocalAccountList(List<LocalAccount> localAccountList) {
        this.localAccountList = localAccountList;
    }
}