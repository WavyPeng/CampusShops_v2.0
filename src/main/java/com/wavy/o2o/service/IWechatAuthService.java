package com.wavy.o2o.service;

import com.wavy.o2o.dto.WechatAuthDto;
import com.wavy.o2o.entity.WechatAccount;
import com.wavy.o2o.exception.WechatAuthOperationException;

/**
 * Created by WavyPeng on 2018/6/28.
 */
public interface IWechatAuthService {
    /**
     * 通过openId查找平台对应的微信帐号
     * @param openId
     * @return
     */
    WechatAccount getWechatAccountByOpenId(String openId);

    /**
     * 注册本平台的微信账号
     * @param wechatAccount
     * @return
     * @throws WechatAuthOperationException
     */
    WechatAuthDto register(WechatAccount wechatAccount) throws WechatAuthOperationException;
}