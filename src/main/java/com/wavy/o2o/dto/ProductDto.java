package com.wavy.o2o.dto;

import com.wavy.o2o.entity.Product;
import com.wavy.o2o.enums.ProductStateEnum;

import java.util.List;

/**
 * 商品
 * Created by WavyPeng on 2018/6/12.
 */
public class ProductDto {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 商品数量
    private int count;

    // 操作的product（增删改商品的时候用）
    private Product product;

    // 获取的product列表(查询商品列表的时候用)
    private List<Product> productList;

    public ProductDto() {
    }

    // 失败的构造器
    public ProductDto(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public ProductDto(ProductStateEnum stateEnum, Product product) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.product = product;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}