package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.UserInfoDao;
import com.wavy.o2o.dao.WechatAccountDao;
import com.wavy.o2o.dto.WechatAuthDto;
import com.wavy.o2o.entity.UserInfo;
import com.wavy.o2o.entity.WechatAccount;
import com.wavy.o2o.enums.WechatAuthStateEnum;
import com.wavy.o2o.exception.WechatAuthOperationException;
import com.wavy.o2o.service.IWechatAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class WechatAuthServiceImpl implements IWechatAuthService {
    private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
    @Autowired
    private WechatAccountDao wechatAccountDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public WechatAccount getWechatAccountByOpenId(String openId) {
        return wechatAccountDao.queryWechatInfoByOpenId(openId);
    }

    @Override
    @Transactional
    public WechatAuthDto register(WechatAccount wechatAccount) throws WechatAuthOperationException {
        //空值判断
        if (wechatAccount == null || wechatAccount.getOpenId() == null) {
            return new WechatAuthDto(WechatAuthStateEnum.NULL_AUTH_INFO);
        }
        try {
            //设置创建时间
            wechatAccount.setCreateTime(new Date());
            //如果微信帐号里夹带着用户信息并且用户Id为空，则认为该用户第一次使用平台(且通过微信登录)
            //则自动创建用户信息
            if (wechatAccount.getUserInfo() != null && wechatAccount.getUserInfo().getUserId() == null) {
                try {
                    wechatAccount.getUserInfo().setCreateTime(new Date());
                    wechatAccount.getUserInfo().setEnableStatus(1);
                    UserInfo userInfo = wechatAccount.getUserInfo();
                    int effectedNum = userInfoDao.insertUserInfo(userInfo);
                    wechatAccount.setUserInfo(userInfo);
                    if (effectedNum <= 0) {
                        throw new WechatAuthOperationException("添加用户信息失败");
                    }
                } catch (Exception e) {
                    log.error("insertPersonInfo error:" + e.toString());
                    throw new WechatAuthOperationException("insertPersonInfo error: " + e.getMessage());
                }
            }
            //创建专属于本平台的微信帐号
            int effectedNum = wechatAccountDao.insertWechatAccount(wechatAccount);
            if (effectedNum <= 0) {
                throw new WechatAuthOperationException("帐号创建失败");
            } else {
                return new WechatAuthDto(WechatAuthStateEnum.SUCCESS, wechatAccount);
            }
        } catch (Exception e) {
            log.error("insertWechatAuth error:" + e.toString());
            throw new WechatAuthOperationException("insertWechatAuth error: " + e.getMessage());
        }
    }
}