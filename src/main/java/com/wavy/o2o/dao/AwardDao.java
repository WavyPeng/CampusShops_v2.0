package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;

/**
 * Created by WavyPeng on 2018/7/17.
 */
public interface AwardDao {
    /**
     * 结合queryAwardList返回相同查询条件下的奖品数
     * @param awardCondition
     * @return
     */
    int queryAwardCount(@Param("awardCondition")Award awardCondition);
}
