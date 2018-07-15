package com.wavy.o2o.service;

import com.wavy.o2o.dto.ProductCategoryDto;
import com.wavy.o2o.entity.ProductCategory;
import com.wavy.o2o.exception.ProductCategoryOperationException;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/11.
 */
public interface IProductCategoryService {
    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品种类
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryDto addProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryDto deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException;
}