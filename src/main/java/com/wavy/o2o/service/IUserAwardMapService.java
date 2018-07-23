package com.wavy.o2o.service;

import com.wavy.o2o.dto.UserAwardMapDto;
import com.wavy.o2o.entity.UserAwardMap;
import com.wavy.o2o.exception.UserAwardMapOperationException;

/**
 * Created by WavyPeng on 2018/7/23.
 */
public interface IUserAwardMapService {

    /**
     * 根据传入的查询条件分页获取列表及总数
     * @param userAwardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserAwardMapDto listUserAwardMap(UserAwardMap userAwardCondition,Integer pageIndex,Integer pageSize);

    /**
     * 根据传入的ID获取映射信息
     * @param userAwardMapId
     * @return
     */
    UserAwardMap getUserAwardMapById(long userAwardMapId);

    /**
     * 领取奖品，添加记录
     * @param userAwardMap
     * @return
     */
    UserAwardMapDto addUserAwardMap(UserAwardMap userAwardMap)throws UserAwardMapOperationException;

    /**
     * 修改奖品领取状态
     * @param userAwardMap
     * @return
     */
    UserAwardMapDto modifyUserrAwardMap(UserAwardMap userAwardMap)throws UserAwardMapOperationException;
}
