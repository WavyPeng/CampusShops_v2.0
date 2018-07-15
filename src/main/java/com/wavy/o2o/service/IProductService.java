package com.wavy.o2o.service;

import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ProductDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.exception.ProductOperationException;

import java.util.List;

/**
 * Created by WavyPeng on 2018/6/12.
 */
public interface IProductService {
    /**
     * 添加商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     */
    ProductDto addProduct(Product product, ImageDto thumbnail, List<ImageDto> productImgList)
            throws ProductOperationException;

    /**
     * 查询商品列表并分页，可输入的条件有： 商品名（模糊），商品状态，店铺Id,商品类别
     * @param productCondition
     * @param pageIndex 页码
     * @param pageSize 每页显示记录数
     * @return
     */
    ProductDto getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 通过商品Id查询唯一的商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductDto modifyProduct(Product product, ImageDto thumbnail, List<ImageDto> productImgList)
            throws ProductOperationException;
}