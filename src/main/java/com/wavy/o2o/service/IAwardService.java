package com.wavy.o2o.service;

import com.wavy.o2o.dto.AwardDto;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.entity.Award;
import com.wavy.o2o.util.ImageUtil;

/**
 * Created by WavyPeng on 2018/7/23.
 */
public interface IAwardService {

    /**
     * 返回奖品列表
     * @param awardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    AwardDto getAwardList(Award awardCondition,int pageIndex,int pageSize);

    /**
     * 根据awardId查询奖品信息
     * @param awardId
     * @return
     */
    Award getAwardById(long awardId);

    /**
     * 添加奖品信息，并添加奖品图片
     * @param award
     * @param thumbnail
     * @return
     */
    AwardDto addAward(Award award, ImageDto thumbnail);

    /**
     * 修改奖品信息，并替换原有图片
     * @param award
     * @param thumbnail
     * @return
     */
    AwardDto modifyAward(Award award, ImageDto thumbnail);
}
