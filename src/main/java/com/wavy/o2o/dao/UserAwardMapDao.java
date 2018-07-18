package com.wavy.o2o.dao;

import com.wavy.o2o.entity.UserAwardMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WavyPeng on 2018/7/18.
 */
public interface UserAwardMapDao {
    /**
     * 根据传入进来的查询条件分页返回用户兑换奖品记录的列表信息
     * @param userAwardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<UserAwardMap> queryUserAwardMapList(@Param("userAwardCondition")UserAwardMap userAwardCondition,
                                             @Param("rowIndex")int rowIndex,
                                             @Param("pageSize")int pageSize);
}
