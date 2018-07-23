package com.wavy.o2o.service;

import com.wavy.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

/**
 * Created by WavyPeng on 2018/7/22.
 */
public interface IProductSellDailyService {
    /**
     * 每日定时为所有店铺的商品销量进行统计
     */
    void dailyCalculate();

    /**
     * 根据查询条件返回商品日销量的统计列表
     * @param productSellDailyCondition
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime);
}
