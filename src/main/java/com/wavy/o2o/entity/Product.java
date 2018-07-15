package com.wavy.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品信息
 * Created by WavyPeng on 2018/5/31.
 */
public class Product {
    // 主键ID
    private Long productId;
    // 商品名
    private String productName;
    // 商品简介
    private String productDesc;
    // 简略图
    private String imgAddr;
    // 原价
    private String originalPrice;
    // 折扣价
    private String discountPrice;
    // 权重，越大越排前显示
    private Integer priority;
    // 商品积分（新添）
    private Integer point;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date lastEditTime;
    // 0.下架 1.在前端展示系统展示
    private Integer enableStatus;

    // 图片详情图列表，跟商品是多对一的关系
    private List<ProductImg> productImgList;
    // 商品类别，一件商品仅属于一个商品类别
    private ProductCategory productCategory;
    // 店铺实体类，标明商品属于哪个店铺
    private Shop shop;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<ProductImg> getProductImgList() {
        return productImgList;
    }

    public void setProductImgList(List<ProductImg> productImgList) {
        this.productImgList = productImgList;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
