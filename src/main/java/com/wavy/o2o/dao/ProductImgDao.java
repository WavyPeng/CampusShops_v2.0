package com.wavy.o2o.dao;

import com.wavy.o2o.entity.ProductImg;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/12.
 */
public interface ProductImgDao {
    /**
     * 批量添加商品详情图片
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有详情图
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 列出某个商品的详情图列表
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);
}