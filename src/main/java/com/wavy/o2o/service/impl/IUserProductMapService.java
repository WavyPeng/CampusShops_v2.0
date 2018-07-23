package com.wavy.o2o.service.impl;

import com.wavy.o2o.dto.UserProductMapDto;
import com.wavy.o2o.entity.UserProductMap;

/**
 * Created by WavyPeng on 2018/7/23.
 */
public interface IUserProductMapService {
    /**
     * 通过传入的查询条件分页列出用户消费信息列表
     * @param userProductCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserProductMapDto listUserProductMap(UserProductMap userProductCondition,Integer pageIndex,Integer pageSize);
}
