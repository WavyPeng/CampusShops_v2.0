package com.wavy.o2o.entity;

import java.util.Date;

/**
 * 顾客每天消费的商品映射
 * Created by WavyPeng on 2018/7/15.
 */
public class ProductSellDaily {
    /**主键ID */
    private Long productSellDailyId;
    /**记录哪天的销量（单位：天） */
    private Date createTime;
    /**销量 */
    private Integer total;
    /**商品信息实体类 */
    private Product product;
    /**店铺信息实体类 */
    private Shop shop;

    public Long getProductSellDailyId() {
        return productSellDailyId;
    }

    public void setProductSellDailyId(Long productSellDailyId) {
        this.productSellDailyId = productSellDailyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
