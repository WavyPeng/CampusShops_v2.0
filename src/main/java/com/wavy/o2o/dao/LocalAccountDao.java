package com.wavy.o2o.dao;

import com.wavy.o2o.entity.LocalAccount;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by WavyPeng on 2018/6/29.
 */
public interface LocalAccountDao {
    /**
     * 按账号密码查询
     * @param username
     * @param password
     * @return
     */
    LocalAccount queryByNameAndPwd(@Param("username") String username, @Param("password") String password);

    /**
     * 按用户id查询
     * @param userId
     * @return
     */
    LocalAccount queryById(@Param("userId") long userId);

    /**
     * 插入新账号
     * @param localAccount
     * @return
     */
    int insertLocalAccount(LocalAccount localAccount);

    /**
     * 修改本地账号信息
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
                        @Param("password") String password, @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}