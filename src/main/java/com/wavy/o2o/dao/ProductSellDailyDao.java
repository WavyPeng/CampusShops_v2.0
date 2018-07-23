package com.wavy.o2o.dao;

import com.wavy.o2o.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WavyPeng on 2018/7/19.
 */
public interface ProductSellDailyDao {
    /**
     * 根据查询条件返回商品日销售的统计列表
     * @param productSellDailyCondition
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> queryProductSellDailyList(@Param("productSellDailyCondition")ProductSellDaily productSellDailyCondition,
                                                     @Param("beginTime")Date beginTime,
                                                     @Param("endTime")Date endTime);

    /**
     * 统计平台所有商品的日销售量
     * @return
     */
    int insertProductSellDaily();

    /**
     * 统计平台当天没有销量的商品，补全信息，将销量设置为0
     * @return
     */
    int insertDefaultProductSellDaily();
}
