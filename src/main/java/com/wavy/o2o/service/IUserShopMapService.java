package com.wavy.o2o.service;

import com.wavy.o2o.dto.UserShopMapDto;
import com.wavy.o2o.entity.UserShopMap;

/**
 * reated by WavyPeng on 2018/7/23.
 */
public interface IUserShopMapService {
    /**
     * 根据传入的查询信息分页查询用户积分列表
     * @param userShopMapCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserShopMapDto listUserShopMap(UserShopMap userShopMapCondition,int pageIndex,int pageSize);

    /**
     * 根据用户Id和店铺Id返回该用户在某个店铺的积分情况
     * @param userId
     * @param shopId
     * @return
     */
    UserShopMap getUserShopMap(long userId,long shopId);
}
