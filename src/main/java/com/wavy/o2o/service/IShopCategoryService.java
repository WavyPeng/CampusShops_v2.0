package com.wavy.o2o.service;

import com.wavy.o2o.entity.ShopCategory;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/4.
 */
public interface IShopCategoryService {

    public static final String SCLISTKEY = "shopcategorylist";

    /**
     * 根据查询条件获取ShopCategory列表
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}