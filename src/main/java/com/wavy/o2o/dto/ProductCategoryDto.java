package com.wavy.o2o.dto;

import com.wavy.o2o.entity.ProductCategory;
import com.wavy.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

/**
 * 产品类别
 * Created by WavyPeng on 2018/6/11.
 */
public class ProductCategoryDto {
    // 结果状态
    private int state;
    // 状态信息
    private String stateInfo;
    // 商品种类列表
    private List<ProductCategory> productCategoryList;

    public ProductCategoryDto() {
    }

    // 操作失败时的构造器
    public ProductCategoryDto(ProductCategoryStateEnum productCategoryStateEnum){
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
    }

    // 操作成功时的构造器
    public ProductCategoryDto(ProductCategoryStateEnum productCategoryStateEnum,
                              List<ProductCategory> productCategoryList){
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}