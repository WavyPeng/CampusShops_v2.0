package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/1.
 */
public interface ShopDao {
    /**
     * 添加商铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 修改商铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shop Id查询店铺
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有：店铺名(模糊),店铺状态，店铺类别，区域Id,owner
     *
     * @param shopCondition
     * @param rowIndex
     *            从第几行开始取数据
     * @param pageSize
     *            返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
