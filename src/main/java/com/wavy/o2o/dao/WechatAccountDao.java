package com.wavy.o2o.dao;

import com.wavy.o2o.entity.WechatAccount;

/**
 * Created by WavyPeng on 2018/6/28.
 */
public interface WechatAccountDao {
    /**
     * 通过openId查询对应本平台的微信帐号
     * @param openId
     * @return
     */
    WechatAccount queryWechatInfoByOpenId(String openId);

    /**
     * 添加对应本平台的微信帐号
     * @param account
     * @return
     */
    int insertWechatAccount(WechatAccount account);
}