package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.LocalAccountDao;
import com.wavy.o2o.dto.LocalAuthDto;
import com.wavy.o2o.entity.LocalAccount;
import com.wavy.o2o.enums.LocalAuthStateEnum;
import com.wavy.o2o.exception.LocalAuthOperationException;
import com.wavy.o2o.service.ILocalAuthService;
import com.wavy.o2o.util.encrypt.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LocalAuthService implements ILocalAuthService {

    @Autowired
    private LocalAccountDao localAccountDao;

    @Override
    public LocalAccount getByUsernameAndPwd(String username, String password) {
        return localAccountDao.queryByNameAndPwd(username, MD5Util.getMd5(password));
    }

    @Override
    public LocalAccount getByUserId(long userId) {
        return localAccountDao.queryById(userId);
    }

    @Override
    @Transactional
    public LocalAuthDto bindLocalAccount(LocalAccount localAccount) throws LocalAuthOperationException {
        // 空值判断
        if(localAccount==null || localAccount.getUsername()==null || localAccount.getPassword()==null ||
                localAccount.getUserInfo()==null || localAccount.getUserInfo().getUserId()==null){
            return new LocalAuthDto(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        // 查询用户是否已经绑定过平台账号
        LocalAccount tmpAccount = localAccountDao.queryByNameAndPwd(localAccount.getUsername(),localAccount.getPassword());
        if(tmpAccount!=null){
            // 如果绑定过则直接退出，以保证平台帐号的唯一性
            return new LocalAuthDto(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try{
            // 如果之前未绑定过账号
            localAccount.setCreateTime(new Date());
            localAccount.setLastEditTime(new Date());
            // 对密码进行MD5加密
            localAccount.setPassword(MD5Util.getMd5(localAccount.getPassword()));
            int effectedNum = localAccountDao.insertLocalAccount(localAccount);
            if(effectedNum<=0){
                throw new LocalAuthOperationException("帐号绑定失败");
            }else{
                return new LocalAuthDto(LocalAuthStateEnum.SUCCESS,localAccount);
            }
        }catch (LocalAuthOperationException e) {
            throw new LocalAuthOperationException("insertLocalAuth error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthDto modifyLocalAccount(Long userId, String username, String password, String newPassword)
            throws LocalAuthOperationException {
        // 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if (userId != null && username != null && password != null && newPassword != null
                && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = localAccountDao.updateLocalAuth(userId, username, MD5Util.getMd5(password),
                        MD5Util.getMd5(newPassword), new Date());
                // 判断更新是否成功
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新密码失败");
                }
                return new LocalAuthDto(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthDto(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}