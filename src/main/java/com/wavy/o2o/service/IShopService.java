package com.wavy.o2o.service;

import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ShopDto;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.exception.ShopOperationException;

/**
 * Created by WavyPeng on 2018/6/3.
 */
public interface IShopService {
    /**
     * 注册店铺
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopDto addShop(Shop shop, ImageDto thumbnail)throws ShopOperationException;

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     * @throws ShopOperationException
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopDto modifyShop(Shop shop, ImageDto thumbnail) throws ShopOperationException;

    /**
     * 根据shopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex 页码
     * @param pageSize  每页显示的商品数
     * @return
     */
    ShopDto getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
