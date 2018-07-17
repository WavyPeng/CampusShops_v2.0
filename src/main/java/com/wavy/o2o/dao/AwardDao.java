package com.wavy.o2o.dao;

import com.wavy.o2o.entity.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WavyPeng on 2018/7/17.
 */
public interface AwardDao {

    /**
     * 依据传入的查询条件分页显示奖品信息列表
     * @param awardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Award> queryAwardList(@Param("awardCondition")Award awardCondition,
                               @Param("rowIndex")int rowIndex,
                               @Param("pageSize")int pageSize);

    /**
     * 结合queryAwardList返回相同查询条件下的奖品数
     * @param awardCondition
     * @return
     */
    int queryAwardCount(@Param("awardCondition")Award awardCondition);

    /**
     * 通过awardId查询奖品信息
     * @param awardId
     * @return
     */
    Award queryAwardByAwardId(long awardId);

    /**
     * 添加奖品信息
     * @param award
     * @return
     */
    int insertAward(Award award);

    /**
     * 更新奖品信息
     * @param award
     * @return
     */
    int updateAward(Award award);

    /**
     * 删除奖品信息
     * @param awardId
     * @param shopId
     * @return
     */
    int deleteAward(@Param("awardId")long awardId,@Param("shopId")long shopId);
}
