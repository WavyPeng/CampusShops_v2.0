package com.wavy.o2o.service;

/**
 * Created by WavyPeng on 2018/7/22.
 */
public interface IProductSellDailyService {
    /**
     * 每日定时为所有店铺的商品销量进行统计
     */
    void dailyCalculate();
}
