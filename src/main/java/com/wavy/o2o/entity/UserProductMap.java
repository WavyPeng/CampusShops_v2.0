package com.wavy.o2o.entity;

import java.util.Date;

/**
 * 顾客-消费商品记录
 * Created by WavyPeng on 2018/7/15.
 */
public class UserProductMap {
    /**主键Id */
    private Long userProductId;
    /**创建时间 */
    private Date createTime;
    /**消费商品所获得的积分 */
    private Integer point;
    /**顾客信息实体类 */
    private UserInfo user;
    /**商品信息实体类 */
    private Product product;
    /**店铺信息实体类 */
    private Shop shop;
    /**操作员信息实体类 */
    private UserInfo operator;

    public Long getUserProductId() {
        return userProductId;
    }

    public void setUserProductId(Long userProductId) {
        this.userProductId = userProductId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
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

    public UserInfo getOperator() {
        return operator;
    }

    public void setOperator(UserInfo operator) {
        this.operator = operator;
    }
}
