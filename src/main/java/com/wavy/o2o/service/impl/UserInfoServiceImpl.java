package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.UserInfoDao;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserInfoById(Long userId) {
        return userInfoDao.queryUserInfoById(userId);
    }
}