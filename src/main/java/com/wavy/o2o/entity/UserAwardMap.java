package com.wavy.o2o.entity;

import java.util.Date;

/**
 * 用户-奖品记录
 * Created by WavyPeng on 2018/7/15.
 */
public class UserAwardMap {
    /**主键Id */
    private Long userAwardId;
    /**创建时间 */
    private Date createTime;
    /**兑换状态 0-未兑换 1-已兑换*/
    private Integer usedStatus;
    /**领取奖品所耗的积分*/
    private Integer point;
    /**顾客信息实体类 */
    private UserInfo user;
    /**奖品信息实体类 */
    private Award award;
    /**店铺信息实体类 */
    private Shop shop;
    /**操作员信息实体类 */
    private UserInfo operator;

    public Long getUserAwardId() {
        return userAwardId;
    }

    public void setUserAwardId(Long userAwardId) {
        this.userAwardId = userAwardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(Integer usedStatus) {
        this.usedStatus = usedStatus;
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

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
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
