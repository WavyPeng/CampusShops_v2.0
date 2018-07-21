package com.wavy.o2o.service;


import com.wavy.o2o.dto.ShopAuthMapDto;
import com.wavy.o2o.entity.ShopAuthMap;
import com.wavy.o2o.exception.ShopAuthMapOperationException;

/**
 * Created by WavyPeng on 2018/7/21.
 */
public interface ShopAuthMapService {
    /**
     * 根据店铺ID分页显示该店铺的授权信息
     * @param shopId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopAuthMapDto listShopAuthMapByShopId(Long shopId,Integer pageIndex,Integer pageSize);

    /**
     * 根据shopAuthId返回对应的授权信息
     * @param shopAuthId
     * @return
     */
    ShopAuthMap getShopAuthMapById(Long shopAuthId);

    /**
     * 添加授权信息（扫码时涉及）
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    ShopAuthMapDto addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;

    /**
     * 更新授权信息
     * @param shopAuthMap
     * @return
     * @throws ShopAuthMapOperationException
     */
    ShopAuthMapDto modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;
}
