package com.wavy.o2o.dao;

import com.wavy.o2o.entity.UserInfo;

/**
 * Created by WavyPeng on 2018/6/28.
 */
public interface UserInfoDao {
    /**
     * 通过用户Id查询用户
     *
     * @param userId
     * @return
     */
    UserInfo queryUserInfoById(long userId);

    /**
     * 添加用户信息
     *
     * @param userInfo
     * @return
     */
    int insertUserInfo(UserInfo userInfo);
}