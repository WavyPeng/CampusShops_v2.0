package com.wavy.o2o.service;

import com.wavy.o2o.dto.LocalAuthDto;
import com.wavy.o2o.entity.LocalAccount;
import com.wavy.o2o.exception.LocalAuthOperationException;

/**
 * Created by WavyPeng on 2018/6/30.
 */
public interface ILocalAuthService {
    /**
     * 通过用户名、密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    LocalAccount getByUsernameAndPwd(String username, String password);

    /**
     * 通过用户Id获取用户信息
     * @param userId
     * @return
     */
    LocalAccount getByUserId(long userId);

    /**
     * 绑定微信，生成平台专属账号
     * @param localAccount
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthDto bindLocalAccount(LocalAccount localAccount) throws LocalAuthOperationException;

    /**
     * 修改平台账号信息
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthDto modifyLocalAccount(Long userId, String username, String password, String newPassword)
            throws LocalAuthOperationException;
}