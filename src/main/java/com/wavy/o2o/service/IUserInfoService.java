package com.wavy.o2o.service;

import com.wavy.o2o.entity.UserInfo;

/**
 * Created by WavyPeng on 2018/6/28.
 */
public interface IUserInfoService {
    /**
     * 根据用户Id获取userInfo信息
     * @param userId
     * @return
     */
    UserInfo getUserInfoById(Long userId);
}