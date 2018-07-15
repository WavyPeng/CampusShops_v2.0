package com.wavy.o2o.dao;

import com.wavy.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/4.
 */
public interface ShopCategoryDao {
    /**
     * 根据传入的查询条件返回店铺类别列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}